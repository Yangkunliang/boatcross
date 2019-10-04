package com.kelystor.boatcross.dao;

import com.kelystor.boatcross.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    void save(Role role);

    List<Role> findRoleList();

    void delete(@Param("deleteRoleIds") List<Long> deleteRoleIds);
}
