package com.kelystor.boatcross.websocket.endpoint;

import com.kelystor.boatcross.config.BcSpringConfigurator;
import com.kelystor.boatcross.entity.JenkinsProject;
import com.kelystor.boatcross.service.JenkinsProjectEvent;
import com.kelystor.boatcross.service.JenkinsProjectService;
import com.kelystor.boatcross.util.ContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ServerEndpoint(
        value = "/jenkins-project",
        configurator = BcSpringConfigurator.class,
        encoders = JenkinsDeployResultEncoder.class,
        decoders = JenkinsDeployRequestDecoder.class
)
@Component
/**
 * 这个类是单例，所以，每个websocket连接，实际上都是使用同一个对象实例
 * 因此不要在类中声明跟连接有关的变量，如：
 * private Session session;
 * 这会导致后续的连接覆盖到前面的连接
 * 如果要声明跟连接有关的变量，也可以用@Scope("prototype")来注解当前类，这样每个连接都使用一个新的对象实例
 * 因为代码中对session的使用很简单，同时也为了考虑性能，依然使用单例方式
 */
public class JenkinsProjectEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(JenkinsProjectEndpoint.class);

    @Autowired
    private JenkinsProjectService jenkinsProjectService;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param deployRequest 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(JenkinsDeployRequest deployRequest, Session session) {
        jenkinsProjectService.build(deployRequest.getProjects(), ContextUtil.currentDeployEnvironment(), new JenkinsProjectEvent() {
            @Override
            public void onSorted(List<JenkinsProject> projects) {
                for (int i = 0; i < projects.size(); i++) {
                    JenkinsDeployResult result = JenkinsDeployResult.build(projects.get(i).getName(), String.format("等待中（顺序%s）", i + 1));
                    sendMessage(result, session);
                }
            }

            @Override
            public void onTriggerBuild(JenkinsProject project) {
                JenkinsDeployResult result = JenkinsDeployResult.build(project.getName(), "正在触发构建");
                sendMessage(result, session);
            }

            @Override
            public void onBuilding(JenkinsProject project) {
                JenkinsDeployResult result = JenkinsDeployResult.build(project.getName(), String.format("构建中%s", stringRepeat(".", ThreadLocalRandom.current().nextInt(8) + 1)));
                sendMessage(result, session);
            }

            @Override
            public void onFinishBuild(JenkinsProject project) {
                JenkinsDeployResult result = JenkinsDeployResult.build(project.getName(), "构建完成");
                sendMessage(result, session);
            }

            @Override
            public void onBuildSuccess(JenkinsProject project, String newVersion) {
                JenkinsDeployResult result = JenkinsDeployResult.build(project.getName(), newVersion == null ? "构建成功，但没有版本号" : "构建完成，版本号 " + newVersion);
                sendMessage(result, session);
            }

            @Override
            public void onBuildFailed(JenkinsProject project, String message) {
                JenkinsDeployResult result = JenkinsDeployResult.build(project.getName(), message);
                sendMessage(result, session);
            }

            @Override
            public void onBuildError(JenkinsProject project, String message) {
                JenkinsDeployResult result = JenkinsDeployResult.build(project.getName(), "构建出错：" + message);
                sendMessage(result, session);
            }

            @Override
            public void onComplete() {
                JenkinsDeployResult result = JenkinsDeployResult.complete();
                sendMessage(result, session);
            }
        });
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        LOGGER.error("websocket发生错误", error);
    }

    private void sendMessage(JenkinsDeployResult result, Session session) {
        try {
            session.getBasicRemote().sendObject(result);
        } catch (IOException | EncodeException e) {
            LOGGER.error("websocket发送消息异常", e);
        }
    }

    private String stringRepeat(String str, int times) {
        return String.join("", Collections.nCopies(times, str));
    }

}