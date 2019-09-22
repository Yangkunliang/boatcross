package com.kelystor.boatcross.controller;

import com.kelystor.boatcross.annotation.CurrentDeployEnvironment;
import com.kelystor.boatcross.enums.DeployEnvironment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerGlobalHandler {
    @ModelAttribute
    public void commonAttribute(Model model, @CurrentDeployEnvironment DeployEnvironment deployEnvironment) {
        model.addAttribute("currentDeployEnvironment", deployEnvironment);
    }
}
