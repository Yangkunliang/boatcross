package com.kelystor.boatcross.controller;

import com.kelystor.boatcross.dao.DeployApplyMapper;
import com.kelystor.boatcross.dao.DeployProjectApplyMapper;
import com.kelystor.boatcross.entity.DeployApply;
import com.kelystor.boatcross.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/deploy")
public class DeployController {
    @Autowired
    private DeployApplyMapper deployApplyMapper;
    @Autowired
    private DeployProjectApplyMapper deployProjectApplyMapper;
    @Autowired
    private SettingService settingService;

    @RequestMapping("/approve")
    public String index(Model model) {
        model.addAttribute("deployApplyList", deployApplyMapper.findAll());
        return "deploy/approve";
    }

    @RequestMapping("/approveView")
    public String approveView(Model model, Integer daId) {
        DeployApply deployApply = deployApplyMapper.findById(daId);
        model.addAttribute("deployApply", deployApply);
        model.addAttribute("jenkinsConfig", settingService.getJenkinsConfig(deployApply.getEnv()));
        model.addAttribute("deployProjectApplyList", deployProjectApplyMapper.findByDaId(daId));
        return "deploy/approveView";
    }
}
