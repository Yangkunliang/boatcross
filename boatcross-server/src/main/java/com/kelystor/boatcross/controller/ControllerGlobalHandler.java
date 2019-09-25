package com.kelystor.boatcross.controller;

import com.kelystor.boatcross.annotation.CurrentDeployEnvironment;
import com.kelystor.boatcross.annotation.CurrentUser;
import com.kelystor.boatcross.entity.User;
import com.kelystor.boatcross.enums.DeployEnvironment;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ControllerGlobalHandler {
    @ModelAttribute
    public void commonAttribute(Model model, @CurrentUser User user, @CurrentDeployEnvironment DeployEnvironment deployEnvironment) {
        model.addAttribute("currentUser", user);
        model.addAttribute("currentDeployEnvironment", deployEnvironment);
    }

    @ExceptionHandler(AuthenticationException.class)
    public String authenticationExceptionHandler(Model model) {
        model.addAttribute("message", "用户名或密码错误");
        return "site/message";
    }
}
