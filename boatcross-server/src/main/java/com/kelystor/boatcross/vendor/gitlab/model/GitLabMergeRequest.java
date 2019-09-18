package com.kelystor.boatcross.vendor.gitlab.model;

import java.util.Date;

public class GitLabMergeRequest {
    private Integer id;
    private Integer iid;
    private Integer projectId;
    private String title;
    private String description;
    private String state;
    private Date createdAt;
    private Date updatedAt;
    private GitLabAuthor mergedBy;
    private Date mergedAt;
    private String targetBranch;
    private String sourceBranch;
    private GitLabAuthor author;
    private String webUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public GitLabAuthor getMergedBy() {
        return mergedBy;
    }

    public void setMergedBy(GitLabAuthor mergedBy) {
        this.mergedBy = mergedBy;
    }

    public Date getMergedAt() {
        return mergedAt;
    }

    public void setMergedAt(Date mergedAt) {
        this.mergedAt = mergedAt;
    }

    public String getTargetBranch() {
        return targetBranch;
    }

    public void setTargetBranch(String targetBranch) {
        this.targetBranch = targetBranch;
    }

    public String getSourceBranch() {
        return sourceBranch;
    }

    public void setSourceBranch(String sourceBranch) {
        this.sourceBranch = sourceBranch;
    }

    public GitLabAuthor getAuthor() {
        return author;
    }

    public void setAuthor(GitLabAuthor author) {
        this.author = author;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @Override
    public String toString() {
        return "GitLabMergeRequest{" +
                "id=" + id +
                ", iid=" + iid +
                ", projectId=" + projectId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", mergedBy=" + mergedBy +
                ", mergedAt='" + mergedAt + '\'' +
                ", targetBranch='" + targetBranch + '\'' +
                ", sourceBranch='" + sourceBranch + '\'' +
                ", author=" + author +
                ", webUrl='" + webUrl + '\'' +
                '}';
    }
}
