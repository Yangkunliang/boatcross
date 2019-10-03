package com.kelystor.boatcross.dao;

import com.kelystor.boatcross.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 权限
 */
@Mapper
public interface PermissionMapper {

    /**
     * 列表数据
     */
    List<Permission> findPermissionList();

    /**
     * 保存
     */
    void save(Permission permission);
}
