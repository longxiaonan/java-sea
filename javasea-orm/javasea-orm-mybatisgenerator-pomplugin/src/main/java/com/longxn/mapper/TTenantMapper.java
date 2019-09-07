package com.longxn.mapper;

import com.longxn.model.TTenant;

public interface TTenantMapper {
    int deleteByPrimaryKey(String tenantId);

    int insert(TTenant record);

    int insertSelective(TTenant record);

    TTenant selectByPrimaryKey(String tenantId);

    int updateByPrimaryKeySelective(TTenant record);

    int updateByPrimaryKey(TTenant record);
}