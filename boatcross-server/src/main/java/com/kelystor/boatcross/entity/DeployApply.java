package com.kelystor.boatcross.entity;

import com.kelystor.boatcross.enums.DeployEnvironment;

import java.io.Serializable;
import java.util.Date;

public class DeployApply implements Serializable {
	/**
	 * id
	 */
	private Integer id;

	/**
	 * 用户id
	 */
	private Integer uid;

	/**
	 * 钉钉审批ID
	 */
	private String processInstanceId;

	/**
	 * 发版说明
	 */
	private String description;

	/**
	 * 环境
	 */
	private DeployEnvironment env;

	/**
	 * 提交时间
	 */
	private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DeployEnvironment getEnv() {
        return env;
    }

    public void setEnv(DeployEnvironment env) {
        this.env = env;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DeployApply{" +
                "id=" + id +
                ", uid=" + uid +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", description='" + description + '\'' +
                ", env='" + env + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}