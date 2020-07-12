package com.ams.system.dao;

import com.ams.system.entity.Material;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaterialMapper {
    /**
     * 根据主键删除物资信息
     */
    int deleteByPrimaryKey(Integer materialId);

    /**
     * 新增物资信息
     */
    int insert(Material record);

    /**
     * 根据主键查询物资信息
     */
    Material selectByPrimaryKey(Integer materialId);

    List<Material> selectAll();

    /**
     * 编辑物资信息
     */
    int updateByPrimaryKey(Material record);

    /**
     * 根据条件查询物资信息
     */
    List<Material> selectAllWithQuery(Material materialQuery);
}