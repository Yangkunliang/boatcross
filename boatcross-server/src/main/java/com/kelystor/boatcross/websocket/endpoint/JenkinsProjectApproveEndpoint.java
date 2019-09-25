package com.kelystor.boatcross.websocket.endpoint;

import com.kelystor.boatcross.config.BcSpringConfigurator;
import com.kelystor.boatcross.entity.DeployProjectApply;
import com.kelystor.boatcross.service.AliyunContainerConfirmProjectUpdateEvent;
import com.kelystor.boatcross.service.AliyunContainerProjectUpdateEvent;
import com.kelystor.boatcross.service.AliyunContainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

@ServerEndpoint(
        value = "/jenkins-project-approve",
        configurator = BcSpringConfigurator.class,
        encoders = JenkinsDeployResultEncoder.class,
        decoders = JenkinsApproveRequestDecoder.class
)
@Component
/**
 * 连接相关的变量使用注意事项，请参考`JenkinsProjectEndpoint`
 */
public class JenkinsProjectApproveEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(JenkinsProjectApproveEndpoint.class);

    @Autowired
    private AliyunContainerService aliyunContainerService;

    @OnOpen
    public void onOpen(Session session) {
    }

    @OnClose
    public void onClose() {
    }

    @OnMessage
    public void onMessage(JenkinsApproveRequest approveRequest, Session session) {
        if (approveRequest.getAction() == JenkinsApproveRequest.ACTION.DEPLOY) {
            deploy(approveRequest.getDaId(), session);
        } else if (approveRequest.getAction() == JenkinsApproveRequest.ACTION.CONFIRM) {
            confirm(approveRequest.getDaId(), session);
        }
    }

    public void deploy(Integer daId, Session session) {
        aliyunContainerService.deploy(daId, new AliyunContainerProjectUpdateEvent() {
            @Override
            public void onDeploying(DeployProjectApply projectApply) {
                JenkinsDeployResult result = JenkinsDeployResult.deploy(projectApply.getProject(), String.format("线上更新中%s", stringRepeat(".", ThreadLocalRandom.current().nextInt(8) + 1)));
                sendMessage(result, session);
            }

            @Override
            public void onFinishDeploy(DeployProjectApply projectApply) {
                JenkinsDeployResult result = JenkinsDeployResult.deploy(projectApply.getProject(), "线上更新完成");
                sendMessage(result, session);
            }

            @Override
            public void onDeployFailed(DeployProjectApply projectApply, String message) {
                JenkinsDeployResult result = JenkinsDeployResult.deploy(projectApply.getProject(), message);
                sendMessage(result, session);
            }

            @Override
            public void onDeployError(DeployProjectApply projectApply, String message) {
                JenkinsDeployResult result = JenkinsDeployResult.deploy(projectApply.getProject(), message);
                sendMessage(result, session);
            }

            @Override
            public void onComplete() {
                JenkinsDeployResult result = JenkinsDeployResult.complete();
                sendMessage(result, session);
            }
        });
    }

    public void confirm(Integer daId, Session session) {
        aliyunContainerService.confirm(daId, new AliyunContainerConfirmProjectUpdateEvent() {
            @Override
            public void onStartConfirm(DeployProjectApply projectApply) {
                JenkinsDeployResult result = JenkinsDeployResult.confirm(projectApply.getProject(), "正在确认发布");
                sendMessage(result, session);
            }

            @Override
            public void onConfirmSuccess(DeployProjectApply projectApply) {
                JenkinsDeployResult result = JenkinsDeployResult.confirm(projectApply.getProject(), "已发布");
                sendMessage(result, session);
            }

            @Override
            public void onConfirmError(DeployProjectApply projectApply, String message) {
                JenkinsDeployResult result = JenkinsDeployResult.confirm(projectApply.getProject(), message);
                sendMessage(result, session);
            }

            @Override
            public void onComplete() {
                JenkinsDeployResult result = JenkinsDeployResult.complete();
                sendMessage(result, session);
            }
        });
    }

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