package com.kelystor.boatcross.vendor.aliyun.container;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kelystor.boatcross.vendor.aliyun.container.model.ContainerProject;
import com.kelystor.boatcross.vendor.exception.ApiRequestException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ContainerAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerAPI.class);
    private static final ObjectMapper MAPPER = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());
    private static final Pattern VERSION_PATTERN = Pattern.compile("v[0-9]{8,}(?=[:']$)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

    public enum UpdateMethod {
        STANDARD,
        BLUE_GREEN,
    }

    private String masterUrl;
    private String certPath;

    private ContainerAPI(String masterUrl, String certPath) {
        this.masterUrl = masterUrl;
        this.certPath = certPath;
    }

    public static ContainerAPI connect(String masterUrl, String certPath) {
        return new ContainerAPI(masterUrl, certPath);
    }

    public ContainerProject projectInfo(String project) {
        HttpGet request = new HttpGet(masterUrl + "/projects/" + sanitizeProjectName(project));
        String content = executeRequest(request);
        return toObject(content, ContainerProject.class);
    }

    public void updateProject(String project, String newVersion, UpdateMethod updateMethod) {
        ContainerProject containerProject = projectInfo(project);

        String template = VERSION_PATTERN.matcher(containerProject.getTemplate()).replaceAll(newVersion);
        Map<String, Object> updateJson = new HashMap<>();
        updateJson.put("version", newVersion);
        updateJson.put("template", template);
        updateJson.put("latest_image", true);
        updateJson.put("description", containerProject.getDescription());
        if (updateMethod == UpdateMethod.BLUE_GREEN) {
            updateJson.put("update_method", "blue-green");
        }

        HttpPost request = new HttpPost(masterUrl + "/projects/" + sanitizeProjectName(project) + "/update");

        StringEntity requestEntity = new StringEntity(toJson(updateJson), StandardCharsets.UTF_8);
        requestEntity.setContentEncoding(StandardCharsets.UTF_8.name());
        request.setHeader("Content-type", "application/json");
        request.setEntity(requestEntity);

        executeRequest(request);
    }

    public void confirmProjectUpdate(String project) {
        HttpPost request = new HttpPost(masterUrl + "/projects/" + sanitizeProjectName(project) + "/confirm-update?force=true");
        executeRequest(request);
    }

    private String executeRequest(HttpUriRequest request) {
        SSLConnectionSocketFactory socketFactory;

        try {
            socketFactory = createSslFactory();
        } catch (IOException e) {
            throw new ApiRequestException("阿里云容器证书路径错误，无法读取证书");
        } catch (CertificateException e) {
            LOGGER.error("阿里云容器证书无效", e);
            throw new ApiRequestException("阿里云容器证书无效");
        } catch (GeneralSecurityException e) {
            LOGGER.error("阿里云容器证书读取异常", e);
            throw new ApiRequestException("阿里云容器证书读取异常");
        }

        // httpClient连接
        try (CloseableHttpClient client = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build()) {
            try (CloseableHttpResponse response = client.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String content = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                if (!Arrays.asList(HttpStatus.SC_OK, HttpStatus.SC_CREATED, HttpStatus.SC_ACCEPTED).contains(statusCode)) {
                    LOGGER.error("阿里云容器请求失败，请求url：{}，状态码：{}，返回内容：{}", request.getURI(), statusCode, content);
                    throw new ApiRequestException("阿里云容器请求失败，状态码：" + statusCode);
                }
                return content;
            }
        } catch (IOException e) {
            throw new ApiRequestException("阿里云容器请求失败，未获取服务器响应");
        }
    }

    private SSLConnectionSocketFactory createSslFactory() throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeySpecException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
        final char[] KEY_STORE_PASSWORD = "".toCharArray();
        //获取证书地址
        Path caCertPath = Paths.get(certPath, "ca.pem");
        Path clientCertPath = Paths.get(certPath, "cert.pem");
        Path clientKeyPath = Paths.get(certPath, "key.pem");

        final CertificateFactory cf = CertificateFactory.getInstance("X.509");
        final Certificate caCert = cf.generateCertificate(Files.newInputStream(caCertPath));
        final Certificate clientCert = cf.generateCertificate(
                Files.newInputStream(clientCertPath));
        final PEMKeyPair clientKeyPair = (PEMKeyPair) new PEMParser(
                Files.newBufferedReader(clientKeyPath,
                        Charset.defaultCharset()))
                .readObject();
        final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(
                clientKeyPair.getPrivateKeyInfo().getEncoded());
        final KeyFactory kf = KeyFactory.getInstance("RSA");
        final PrivateKey clientKey = kf.generatePrivate(spec);
        // 设置信任的证书
        final KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(null, null);
        trustStore.setEntry("ca", new KeyStore.TrustedCertificateEntry(caCert), null);
        // 设置私钥
        final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("client", clientCert);
        keyStore.setKeyEntry("key", clientKey, KEY_STORE_PASSWORD, new Certificate[]{clientCert});
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(trustStore, null)
                .loadKeyMaterial(keyStore, KEY_STORE_PASSWORD)
                .build();
        return new SSLConnectionSocketFactory(
                sslContext,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
    }

    private String sanitizeProjectName(String projectName) {
        try {
            return URLEncoder.encode(projectName, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new ApiRequestException("阿里云容器项目名称 " + projectName + " URLEncoder.encode失败");
        }
    }

    private <T> T toObject(String content, Class<T> valueType) {
        try {
            return MAPPER.readValue(content, valueType);
        } catch (Exception e) {
            throw new ApiRequestException("阿里云容器接口参数转换对象错误", e);
        }
    }

    private String toJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ApiRequestException("阿里云容器接口参数转换Json错误", e);
        }
    }
}
