package com.kelystor.boatcross.service;

import com.kelystor.boatcross.entity.DeployProjectApply;

public interface AliyunContainerConfirmProjectUpdateEvent {
    default void onStartConfirm(DeployProjectApply projectApply) {}

    default void onConfirmSuccess(DeployProjectApply projectApply) {}

    default void onConfirmError(DeployProjectApply projectApply, String message) {}

    default void onComplete() {}
}
