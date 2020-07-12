package com.ams.system.dao;

import com.ams.system.entity.Student;
import com.ams.system.entity.User;
import com.sun.imageio.plugins.common.I18N;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    List<User> selectAll();

    int updateByPrimaryKey(User record);

//   ---------------------------------------------------------------    //

    /**
     * 获取用户所拥有的角色名
     */
    Set<String> selectRoleNameByUsername(@Param("username") String username);

    /**
     * 获取用户所拥有的操作权限
     */
    Set<String> selectOperatorPermsByUsername(@Param("username") String username);

    /**
     * 获取指定用户名的用户信息
     */
    User selectOneByUsername(@Param("username") String username);

    /**
     * 更新用户最后登录时间
     */
    void updateLastLoginTimeByUsername(@Param("username") String username);

    /**
     * 删除指定用户的用户信息
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * 更新用户密码
     */
    void updatePasswordByUserId(@Param("userId") int userId, @Param("password") String encryptPassword, @Param("salt") String salt);

    /**
     * 统计用户名出现的次数
     */
    int countByUsername(@Param("username") String username);

    /**
     * 统计用户名出现的次数 但不包含userId用户
     */
    int countByUsernameNotIncludeUserId(@Param("username") String username, @Param("userId") Integer userId);

    /**
     * 新增用户
     */
    int insert(User record);

    /**
     * 根据主键更新用户信息
     */
    int updateByPrimaryKeySelective(User user);

    /**
     * 更改指定用户的状态
     */
    int updateStatusByUserId(@Param("userId") Integer userId, @Param("status") Integer status);

    /**
     * 将指定的studentId设置为Null
     */
    void setNullWithStudentId(@Param("studentId") int studentId);

    /**
     * 根据查询条件返回用户信息
     */
    List<User> selectAllWithQuery(User userQuery);

    /**
     * 根据userId查询用户
     */
    User selectByPrimaryKey(@Param("userId") Integer userId);

    /**
     * 查询用户的角色ID
     */
    List<Integer> selectRoleIdsByUserId(@Param("userId") Integer userId);

    /**
     * 根据userId查询用户(返回具体学生信息)
     */
    User selectByUserId(@Param("userId") Integer userId);
}