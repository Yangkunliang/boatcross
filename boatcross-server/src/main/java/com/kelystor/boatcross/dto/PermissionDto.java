package com.kelystor.boatcross.dto;

import java.io.Serializable;

public class PermissionDto implements Serializable {
    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 方法访问级别权限类型：0-操作权限，1-资源权限
     */
    private Integer accessType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getAccessType() {
        return accessType;
    }

    public void setAccessType(Integer accessType) {
        this.accessType = accessType;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "name='" + name + '\'' +
                ", permission='" + permission + '\'' +
                ", accessType=" + accessType +
                '}';
    }
}
