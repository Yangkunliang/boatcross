package com.kelystor.boatcross.service;

import com.kelystor.boatcross.dao.DeployApplyMapper;
import com.kelystor.boatcross.dao.DeployProjectApplyMapper;
import com.kelystor.boatcross.dao.JenkinsProjectMapper;
import com.kelystor.boatcross.entity.AliyunContainerConfig;
import com.kelystor.boatcross.entity.DeployApply;
import com.kelystor.boatcross.entity.DeployProjectApply;
import com.kelystor.boatcross.entity.JenkinsProject;
import com.kelystor.boatcross.enums.DeployEnvironment;
import com.kelystor.boatcross.vendor.aliyun.container.ContainerAPI;
import com.kelystor.boatcross.vendor.aliyun.container.model.ContainerProject;
import com.kelystor.boatcross.vendor.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AliyunContainerService {
    @Autowired
    private SettingService settingService;
    @Autowired
    private JenkinsProjectMapper jenkinsProjectMapper;
    @Autowired
    private DeployApplyMapper deployApplyMapper;
    @Autowired
    private DeployProjectApplyMapper deployProjectApplyMapper;

    public void deploy(final Integer daId, final AliyunContainerProjectUpdateEvent event) {
        DeployApply deployApply = deployApplyMapper.findById(daId);
        ContainerAPI containerAPI = buildContainerAPI(deployApply);

        List<DeployProjectApply> deployProjectApplyList = deployProjectApplyMapper.findByDaId(daId);

        DeployProjectApply currentDeployProjectApply = null;
        try {
            for (DeployProjectApply deployProjectApply : deployProjectApplyList) {
                currentDeployProjectApply = deployProjectApply;

                String aliyunProjectName = getAliyunContainerProjectName(deployProjectApply.getProject(), deployApply.getEnv());

                containerAPI.updateProject(aliyunProjectName, deployProjectApply.getVersion(), ContainerAPI.UpdateMethod.BLUE_GREEN);

                ContainerProject containerProject;
                while (true) {
                    containerProject = containerAPI.projectInfo(aliyunProjectName);
                    if (containerProject.getCurrentState().equalsIgnoreCase("updating")) {
                        event.onDeploying(deployProjectApply);
                        TimeUnit.SECONDS.sleep(3);
                        continue;
                    }
                    if (containerProject.getCurrentState().equalsIgnoreCase("running")) {
                        event.onFinishDeploy(deployProjectApply);
                        break;
                    }
                }

                if (Boolean.TRUE == containerProject.getProjectStatusDetail().getIsError()) {
                    event.onDeployFailed(deployProjectApply, "线上更新有错误");
                }
            }
        } catch (InterruptedException ignore) {
        } catch (ApiRequestException e) {
            event.onDeployError(currentDeployProjectApply, e.getMessage());
        } finally {
            event.onComplete();
        }
    }

    public void confirm(final Integer daId, final AliyunContainerConfirmProjectUpdateEvent event) {
        DeployApply deployApply = deployApplyMapper.findById(daId);
        ContainerAPI containerAPI = buildContainerAPI(deployApply);

        List<DeployProjectApply> deployProjectApplyList = deployProjectApplyMapper.findByDaId(daId);

        DeployProjectApply currentDeployProjectApply = null;
        try {
            for (DeployProjectApply deployProjectApply : deployProjectApplyList) {
                currentDeployProjectApply = deployProjectApply;

                String aliyunProjectName = getAliyunContainerProjectName(deployProjectApply.getProject(), deployApply.getEnv());

                event.onStartConfirm(deployProjectApply);
                containerAPI.confirmProjectUpdate(aliyunProjectName);
                event.onConfirmSuccess(deployProjectApply);
            }
        } catch (ApiRequestException e) {
            event.onConfirmError(currentDeployProjectApply, e.getMessage());
        } finally {
            event.onComplete();
        }
    }

    private ContainerAPI buildContainerAPI(DeployApply deployApply) {
        AliyunContainerConfig aliyunContainerConfig = settingService.getAliyunContainerConfig(deployApply.getEnv());
        return ContainerAPI.connect(aliyunContainerConfig.getMasterUrl(), aliyunContainerConfig.getCertPath());
    }

    private String getAliyunContainerProjectName(String projectName, DeployEnvironment environment) {
        JenkinsProject jenkinsProject = jenkinsProjectMapper.findByProjectName(projectName, environment);
        return jenkinsProject.getAliyunContainerProjectName();
    }
}
