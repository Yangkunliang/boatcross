package com.kelystor.boatcross.vo;

import com.kelystor.boatcross.vendor.gitlab.model.GitLabMergeRequest;
import com.kelystor.boatcross.vendor.jenkins.model.JenkinsBuildResult;

public class ProjectApplyVO {
    private String name;
    private JenkinsBuildResult lastBuildResult;
    private String lastBuildVersion;
    private GitLabMergeRequest lastMergeRequest;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JenkinsBuildResult getLastBuildResult() {
        return lastBuildResult;
    }

    public void setLastBuildResult(JenkinsBuildResult lastBuildResult) {
        this.lastBuildResult = lastBuildResult;
    }

    public String getLastBuildVersion() {
        return lastBuildVersion;
    }

    public void setLastBuildVersion(String lastBuildVersion) {
        this.lastBuildVersion = lastBuildVersion;
    }

    public GitLabMergeRequest getLastMergeRequest() {
        return lastMergeRequest;
    }

    public void setLastMergeRequest(GitLabMergeRequest lastMergeRequest) {
        this.lastMergeRequest = lastMergeRequest;
    }
}
