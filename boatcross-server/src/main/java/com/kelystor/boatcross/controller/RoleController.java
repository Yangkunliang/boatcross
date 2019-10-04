package com.kelystor.boatcross.controller;

import com.kelystor.boatcross.annotation.CurrentUser;
import com.kelystor.boatcross.constants.CodeConstants;
import com.kelystor.boatcross.dao.RoleMapper;
import com.kelystor.boatcross.dto.RoleDto;
import com.kelystor.boatcross.dto.WebApiResponse;
import com.kelystor.boatcross.entity.Role;
import com.kelystor.boatcross.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleMapper roleMapper;

    @RequestMapping
    public String index(Model model) {
        List<Role> roleList = roleMapper.findRoleList();
        model.addAttribute("roleList", roleList);
        return "role/index";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse save(RoleDto roleDto, @CurrentUser User user) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        role.setCreateBy(user.getUsername());
        try {
            roleMapper.save(role);
        } catch (Exception e) {
            LOGGER.error("新增角色失败", e);
            return WebApiResponse.fail(CodeConstants.SYSTEM_ERROR, e.getMessage());
        }
        return WebApiResponse.success(role);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse delete(HttpServletRequest request) {
        String[] roleIds = request.getParameterValues("roleIds[]");
        List<Long> deleteRoleIds = new ArrayList<>();
        for (String roleId : roleIds) {
            deleteRoleIds.add(Long.valueOf(roleId));
        }
        try {
            roleMapper.delete(deleteRoleIds);
        } catch (Exception e) {
            LOGGER.error("删除角色失败", e);
            return WebApiResponse.fail(CodeConstants.SYSTEM_ERROR, e.getMessage());
        }
        return WebApiResponse.success(null);
    }
}
