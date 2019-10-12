package com.kelystor.boatcross.dao;

import com.kelystor.boatcross.dto.RolePermissionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author admin
 * @since 2019/10/12 16:19
 */
@Mapper
public interface RolePermissionMapper {
    List<RolePermissionDto> findRolePermisssionList(Long roleId);
}
