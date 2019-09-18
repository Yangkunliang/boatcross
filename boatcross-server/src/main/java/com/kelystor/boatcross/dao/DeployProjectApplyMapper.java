package com.kelystor.boatcross.dao;

import com.kelystor.boatcross.entity.DeployProjectApply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeployProjectApplyMapper {
    List<DeployProjectApply> findDeployProjectApplyDetail(DeployProjectApply deployProjectApply);

    void save(DeployProjectApply deployProjectApply);
}