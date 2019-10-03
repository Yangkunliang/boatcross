package com.kelystor.boatcross.entity;

public class Permission extends BaseEntity {
    /**
     * 权限名称，中文
     */
    private String name;

    /**
     * 权限字符串
     */
    private String permission;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 权限状态
     */
    private Integer status;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAccessType() {
        return accessType;
    }

    public void setAccessType(Integer accessType) {
        this.accessType = accessType;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "name='" + name + '\'' +
                ", permission='" + permission + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", accessType=" + accessType +
                "} " + super.toString();
    }
}
