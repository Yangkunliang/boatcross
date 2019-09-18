package com.kelystor.boatcross.entity;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * uid
	 */
	private Integer uid;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 花名
	 */
	private String nickname;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * gitlab私有token
	 */
	private String gitlabPrivateToken;

	/**
	 * 钉钉用户id
	 */
	private String dingdingUserId;

	/**
	 * 钉钉部门id
	 */
	private Integer dingdingDepartmentId;

	/**
	 * 钉钉部门名称
	 */
	private String dingdingDepartmentName;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGitlabPrivateToken() {
        return gitlabPrivateToken;
    }

    public void setGitlabPrivateToken(String gitlabPrivateToken) {
        this.gitlabPrivateToken = gitlabPrivateToken;
    }

    public String getDingdingUserId() {
        return dingdingUserId;
    }

    public void setDingdingUserId(String dingdingUserId) {
        this.dingdingUserId = dingdingUserId;
    }

    public Integer getDingdingDepartmentId() {
        return dingdingDepartmentId;
    }

    public void setDingdingDepartmentId(Integer dingdingDepartmentId) {
        this.dingdingDepartmentId = dingdingDepartmentId;
    }

    public String getDingdingDepartmentName() {
        return dingdingDepartmentName;
    }

    public void setDingdingDepartmentName(String dingdingDepartmentName) {
        this.dingdingDepartmentName = dingdingDepartmentName;
    }
}