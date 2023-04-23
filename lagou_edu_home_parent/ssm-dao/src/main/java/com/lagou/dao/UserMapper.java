package com.lagou.dao;

import com.lagou.domain.*;

import java.util.List;

public interface UserMapper {

    /**
     *用户分页多条件查询
     */
    public List<User> findAllUserByPage(UserVo userVo);

    /**
     * 用户登录
     */
    public User login(User user);

    /**
     * 根据用户ID清空中间表
     */
    public void deleteUserContextRole(Integer userId);

    /**
     * 分配角色
     */
    public void userContextRole(User_Role_relation user_role_relation);


    /**
     * 根据用户ID查询关联的角色信息
     */
    public List<Role> findUserRelationRoleById(Integer id);

    /**
     * 根据角色ID查询关联的菜单信息
     */
    public List<Menu> findParentMenuByRoleId(List<Integer> ids);

    /**
     * 根据pid，查询子菜单信息
     */
    public List<Menu> findSubMenuByPid(Integer pid);

    /**
     * 获取用户拥有的资源权限信息
     */
    public List<Resource> findResourceByRoleId(List<Integer> ids);
    
    public List<Resource> findResourceByRoleId2(List<Integer> ids);

    public void test1();
    public void test2();
    public void test3();
    public void test4();
    public void test5();
    public void test6();
    public void test7();

}
