package com.kelystor.boatcross.vendor.aliyun.container.model;

import java.util.Date;

public class ContainerProject {
    private String name;
    private String description;
    private String template;
    private String version;
    private String currentState;
    private ProjectStatusDetail projectStatusDetail;
    private Date created;
    private Date updated;

    public static class ProjectStatusDetail {
        private String actionStatus;
        private Boolean isError;

        public String getActionStatus() {
            return actionStatus;
        }

        public void setActionStatus(String actionStatus) {
            this.actionStatus = actionStatus;
        }

        public Boolean getIsError() {
            return isError;
        }

        public void setIsError(Boolean isError) {
            this.isError = isError;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public ProjectStatusDetail getProjectStatusDetail() {
        return projectStatusDetail;
    }

    public void setProjectStatusDetail(ProjectStatusDetail projectStatusDetail) {
        this.projectStatusDetail = projectStatusDetail;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
