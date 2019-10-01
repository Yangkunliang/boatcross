package com.kelystor.boatcross.entity;

public class Role extends BaseEntity{

    /**
     * 角色字符串，英文
     */
    private String role;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述信息
     */
    private String description;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }
}
