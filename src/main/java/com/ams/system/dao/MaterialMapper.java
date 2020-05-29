package com.ams.system.dao;

import com.ams.system.entity.Material;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaterialMapper {
    int deleteByPrimaryKey(Integer materialId);

    int insert(Material record);

    Material selectByPrimaryKey(Integer materialId);

    List<Material> selectAll();

    int updateByPrimaryKey(Material record);
}