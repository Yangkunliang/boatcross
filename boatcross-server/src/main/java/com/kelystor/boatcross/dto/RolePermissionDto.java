package com.kelystor.boatcross.dto;

import com.kelystor.boatcross.entity.Permission;

import java.io.Serializable;
import java.util.List;

/**
 * 角色-权限
 */
public class RolePermissionDto implements Serializable {

    private Long id;

    private Long roleId;

    private List<Permission> permissionList;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
}
