
package com.kelystor.boatcross.controller;

import com.kelystor.boatcross.annotation.CurrentDeployEnvironment;
import com.kelystor.boatcross.entity.AliyunContainerConfig;
import com.kelystor.boatcross.entity.GitLabConfig;
import com.kelystor.boatcross.entity.JenkinsConfig;
import com.kelystor.boatcross.enums.DeployEnvironment;
import com.kelystor.boatcross.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;

    @RequestMapping
    public String list() {
        return "setting/list";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(
            @ModelAttribute(name = "jenkins") JenkinsConfig jenkinsConfig
    ) {
        return "setting/list";
    }

    @RequestMapping("jenkins")
    public String jenkins(Model model, @CurrentDeployEnvironment DeployEnvironment deployEnvironment) {
        model.addAttribute("jenkinsConfig", settingService.getJenkinsConfig(deployEnvironment));
        return "setting/jenkins";
    }

    @RequestMapping(value = "jenkinsUpdate", method = RequestMethod.POST)
    public String jenkinsUpdate(JenkinsConfig jenkinsConfig, @CurrentDeployEnvironment DeployEnvironment deployEnvironment) {
        settingService.update(SettingService.JENKINS_KEY, jenkinsConfig, deployEnvironment);
        return "setting/jenkins";
    }

    @RequestMapping("gitlab")
    public String gitlab(Model model) {
        model.addAttribute("gitLabConfig", settingService.getGitLabConfig());
        return "setting/gitlab";
    }

    @RequestMapping(value = "gitlabUpdate", method = RequestMethod.POST)
    public String gitlabUpdate(GitLabConfig gitLabConfig) {
        settingService.update(SettingService.GITLAB_KEY, gitLabConfig, null);
        return "setting/gitlab";
    }

    @RequestMapping("aliyunContainer")
    public String aliyunContainer(Model model, @CurrentDeployEnvironment DeployEnvironment deployEnvironment) {
        model.addAttribute("aliyunContainerConfig", settingService.getAliyunContainerConfig(deployEnvironment));
        return "setting/aliyunContainer";
    }

    @RequestMapping(value = "aliyunContainerUpdate", method = RequestMethod.POST)
    public String aliyunContainerUpdate(AliyunContainerConfig aliyunContainerConfig, @CurrentDeployEnvironment DeployEnvironment deployEnvironment) {
        settingService.update(SettingService.ALIYUN_CONTAINER_KEY, aliyunContainerConfig, deployEnvironment);
        return "setting/aliyunContainer";
    }

    @RequestMapping("dingding")
    public String dingding(Model model, @CurrentDeployEnvironment DeployEnvironment deployEnvironment) {
        return "setting/dingding";
    }
}
