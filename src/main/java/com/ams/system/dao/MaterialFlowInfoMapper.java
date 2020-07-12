package com.ams.system.dao;

import com.ams.system.entity.MaterialFlowInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaterialFlowInfoMapper {
    /**
     * 删除物资流动信息
     */
    int deleteByPrimaryKey(Integer materialFlowId);

    /**
     * 新增物资流动信息
     */
    int insert(MaterialFlowInfo record);

    /**
     * 根据主键查询物资流动信息
     */
    MaterialFlowInfo selectByPrimaryKey(Integer materialFlowId);

    List<MaterialFlowInfo> selectAll();

    /**
     * 编辑物资流动信息
     */
    int updateByPrimaryKey(MaterialFlowInfo record);

    /**
     * 根据条件查询物资流动信息
     */
    List<MaterialFlowInfo> selectAllWithQuery(MaterialFlowInfo materialFlowInfoQuery);

    /**
     *根据条件查询待审核的物资流动信息
     */
    List<MaterialFlowInfo> selectMaterialFlowInfoAuditsWithQuery(MaterialFlowInfo materialFlowInfoQuery);
}