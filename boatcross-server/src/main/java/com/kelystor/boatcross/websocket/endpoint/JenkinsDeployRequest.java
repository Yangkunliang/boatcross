package com.kelystor.boatcross.websocket.endpoint;

import java.util.List;

public class JenkinsDeployRequest {
    private List<String> projects;

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }
}
