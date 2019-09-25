package com.kelystor.boatcross.entity;

import java.io.Serializable;

public class DeployProjectApply implements Serializable {
	/**
	 * id
	 */
	private Integer id;

	/**
	 * 部署申请id
	 */
	private Integer daId;

	/**
	 * 项目
	 */
	private String project;

	/**
	 * 版本
	 */
	private String version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDaId() {
        return daId;
    }

    public void setDaId(Integer daId) {
        this.daId = daId;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "DeployProjectApply{" +
                "id=" + id +
                ", daId=" + daId +
                ", project='" + project + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}