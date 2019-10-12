package com.kelystor.boatcross.controller;

import com.kelystor.boatcross.annotation.CurrentUser;
import com.kelystor.boatcross.constants.CodeConstants;
import com.kelystor.boatcross.dao.PermissionMapper;
import com.kelystor.boatcross.dto.PermissionDto;
import com.kelystor.boatcross.dto.RolePermissionDto;
import com.kelystor.boatcross.dto.WebApiResponse;
import com.kelystor.boatcross.entity.Permission;
import com.kelystor.boatcross.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);
    @Autowired
    private PermissionMapper permissionMapper;

    @RequestMapping
    public String index(Model model){
        List<Permission> permissionList = permissionMapper.findPermissionList();
        model.addAttribute("permissionList", permissionList);
        return "permission/index";
    }

    @RequestMapping("/save")
    @ResponseBody
    public WebApiResponse save(PermissionDto permissionDto, @CurrentUser User user){
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionDto, permission);
        permission.setCreateBy(user.getUsername());
        try {
            permissionMapper.save(permission);
        } catch (Exception e) {
            LOGGER.error("新增权限失败", e);
            return WebApiResponse.fail(CodeConstants.SYSTEM_ERROR, e.getMessage());
        }
        return WebApiResponse.success(null);
    }

    /**
     * 根据角色ID编辑权限
     */
    @RequestMapping("/editByRoleId")
    @ResponseBody
    public WebApiResponse editByRoleId(Long roleId){
        List<Permission> list = permissionMapper.findPermissionList();
        // TODO: 2019/10/11 需要增加查询角色-权限关联关系，然后前端页面做自动勾选
//        List<RolePermissionDto> rolePermissionDtos =
        return WebApiResponse.success(list);
    }
}
