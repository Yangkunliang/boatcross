package com.kelystor.boatcross.entity;

/**
 * @author admin
 * @since 2019/10/12 16:15
 */
public class RolePermission extends BaseEntity{

    private Long roleId;

    private Long permissionId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "RolePermission{" + "roleId=" + roleId + ", permissionId=" + permissionId + ", createTime="
            + getCreateTime() + ", createBy='" + getCreateBy() + '\'' + ", modifyTime=" + getModifyTime()
            + ", modifyBy='" + getModifyBy() + '\'' + ", id=" + getId() + "} " + super.toString();
    }
}
