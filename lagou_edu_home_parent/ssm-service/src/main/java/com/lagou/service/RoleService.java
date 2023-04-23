package com.lagou.service;

import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVo;

import java.util.List;

public interface RoleService {

    /**
     *查询所有角色
     */
    public List<Role> findAllRole(Role role);


    /**
     * 根据角色ID查询该角色关联的菜单信息ID
     */
    public List<Integer> findMenuByRoleId(Integer roleId);

    /**
     * 添加菜单关联信息
     */
    public void roleContextMenu(RoleMenuVo roleMenuVo);

    /**
     * 删除角色
     */

    public void deleteRole(Integer roleid);
}
