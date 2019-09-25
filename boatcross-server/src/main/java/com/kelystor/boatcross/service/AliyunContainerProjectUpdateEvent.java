package com.kelystor.boatcross.service;

import com.kelystor.boatcross.entity.DeployProjectApply;

public interface AliyunContainerProjectUpdateEvent {
    default void onDeploying(DeployProjectApply projectApply) {}

    default void onFinishDeploy(DeployProjectApply projectApply) {}

    default void onDeployFailed(DeployProjectApply projectApply, String message) {}

    default void onDeployError(DeployProjectApply projectApply, String message) {}

    default void onComplete() {}
}
