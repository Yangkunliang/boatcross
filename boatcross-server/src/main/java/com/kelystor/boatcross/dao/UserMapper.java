package com.kelystor.boatcross.dao;


import com.kelystor.boatcross.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByUid(Integer uid);

    User findByUsername(String username);
}