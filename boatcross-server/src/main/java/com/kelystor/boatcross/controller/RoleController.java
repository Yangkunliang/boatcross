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
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping("/save")
    @ResponseBody
    public WebApiResponse save(RoleDto roleDto, @CurrentUser User user) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        role.setCreateBy(user.getName());
        try {
            roleMapper.save(role);
        } catch (Exception e) {
            LOGGER.error("新增角色失败", e);
            return WebApiResponse.fail(CodeConstants.SYSTEM_ERROR, e.getMessage());
        }
        return WebApiResponse.success(role);
    }
}
