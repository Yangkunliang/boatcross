package com.kelystor.boatcross.websocket.endpoint;

public class JenkinsApproveRequest {
    private Integer daId;
    private ACTION action;

    enum ACTION {
        DEPLOY,
        CONFIRM
    }

    public Integer getDaId() {
        return daId;
    }

    public void setDaId(Integer daId) {
        this.daId = daId;
    }

    public ACTION getAction() {
        return action;
    }

    public void setAction(ACTION action) {
        this.action = action;
    }
}
