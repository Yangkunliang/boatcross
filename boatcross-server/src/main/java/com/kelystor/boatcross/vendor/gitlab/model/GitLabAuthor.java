package com.kelystor.boatcross.vendor.gitlab.model;

public class GitLabAuthor {
    private Integer id;
    private String name;
    private String username;
    private String state;
    private String avatarUrl;
    private String webUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @Override
    public String toString() {
        return "GitLabAuthor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", state='" + state + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", webUrl='" + webUrl + '\'' +
                '}';
    }
}
