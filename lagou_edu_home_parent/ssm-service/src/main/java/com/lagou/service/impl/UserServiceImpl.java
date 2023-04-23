package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    /**
     *用户分页多条件查询
     */
    @Override
    public PageInfo findAllUsrByPage(UserVo userVo) {
        PageHelper.startPage(userVo.getCurrentPage(),userVo.getPageSize());
        List<User> allUserByPage = userMapper.findAllUserByPage(userVo);
        PageInfo<User> pageInfo = new PageInfo<>(allUserByPage);
        return pageInfo;
    }

    /**
     * 用户登录
     */
    @Override
    public User login(User user) throws Exception {

        //调用mapper方法
        User user1 = userMapper.login(user);
        if (user1 != null && Md5.verify(user.getPassword(),"",user1.getPassword())){
            return user1;
        }else {
            return null;
        }
    }

    /**
     * 分配角色
     */
    @Override
    public List<Role> findUserRelationRoleById(Integer id) {
        List<Role> list = userMapper.findUserRelationRoleById(id);
        return list;
    }

    @Override
    public void userContextRole(UserVo userVo) {

        //根据用户ID清空中间表关联关系
        userMapper.deleteUserContextRole(userVo.getUserId());

        //重新建立关联关系
        for (Integer roleId : userVo.getRoleIdList()) {

            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setUserId(userVo.getUserId());
            user_role_relation.setRoleId(roleId);

            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);

            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");
            userMapper.userContextRole(user_role_relation);

        }
    }

    @Override
    public ResponseResult getUserPermissions(Integer userid) {
        //1.获取当前用户拥有的角色
        List<Role> roleList = userMapper.findUserRelationRoleById(userid);
        //2.获取角色ID,保存到 list
        List<Integer> roleIds = new ArrayList<>();
        for (Role role : roleList) {
            roleIds .add(role.getId());
        }
        //3.根据角色id查询 父菜单
        List<Menu> parentMenu = userMapper.findParentMenuByRoleId(roleIds);
        //4.封装父菜单下的子菜单
        for (Menu menu : parentMenu) {
            List<Menu> subMenu = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubMenuList(subMenu);
        }
        //5.获取资源权限
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIds);
        //6.封装数据
        Map<String,Object> map = new HashMap<>();
        map.put("menuList",parentMenu); //menuList: 菜单权限数据
        map.put("resourceList",resourceList);//resourceList: 资源权限数据
        ResponseResult result = new ResponseResult(true,200,"响应成功",map);
        return result;
    }
}
