package com.ams.system.dao;

import com.ams.system.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {

    /**
     * 获取全部菜单
     */
    List<Menu> selectAll();

    /**
     * 获取某个用户的所拥有的导航菜单
     */
    List<Menu> selectMenuByUsername(@Param("username") String username);

    /**
     * 获取全部菜单（树型）
     */
    List<Menu> selectAllTree();

    /**
     * 获取菜单中最大的OrderNum
     */
    int selectMaxOrderNum();

    /**
     * 新增菜单
     */
    int insert(Menu record);

    /**
     * 查询指定菜单的子菜单Id
     */
    List<Integer> selectChildrenIdByMenuId(@Param("menuId") Integer menuId);

    /**
     * 编辑菜单
     */
    int updateByPrimaryKey(Menu record);

    /**
     * 查询指定菜单
     */
    Menu selectByPrimaryKey(Integer menuId);

    /**
     * 删除指定菜单
     */
    int deleteByPrimaryKey(Integer menuId);
}