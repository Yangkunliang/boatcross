package com.kelystor.boatcross.entity;

public class GitLabConfig {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "GitLabConfig{" +
                "url='" + url + '\'' +
                '}';
    }
}
