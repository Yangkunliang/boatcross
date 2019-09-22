package com.kelystor.boatcross.dao;

import com.kelystor.boatcross.entity.DeployApply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeployApplyMapper {
    List<DeployApply> findDeployApplyDetail(DeployApply deployApply);

    DeployApply findById(Integer id);

    List<DeployApply> findAll();

    void save(DeployApply deployApply);
}