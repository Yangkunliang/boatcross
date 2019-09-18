package com.kelystor.boatcross.controller;

import com.kelystor.boatcross.annotation.CurrentDeployEnvironment;
import com.kelystor.boatcross.annotation.CurrentUser;
import com.kelystor.boatcross.dao.DeployApplyMapper;
import com.kelystor.boatcross.dao.DeployProjectApplyMapper;
import com.kelystor.boatcross.dao.JenkinsProjectMapper;
import com.kelystor.boatcross.entity.*;
import com.kelystor.boatcross.enums.DeployEnvironment;
import com.kelystor.boatcross.service.GitLabService;
import com.kelystor.boatcross.service.JenkinsProjectService;
import com.kelystor.boatcross.service.SettingService;
import com.kelystor.boatcross.vendor.gitlab.GitLabAPI;
import com.kelystor.boatcross.vendor.gitlab.model.GitLabMergeRequest;
import com.kelystor.boatcross.vendor.jenkins.JenkinsAPI;
import com.kelystor.boatcross.vendor.jenkins.model.JenkinsBuildResult;
import com.kelystor.boatcross.vo.ProjectApplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private JenkinsProjectMapper jenkinsProjectMapper;
    @Autowired
    private JenkinsProjectService jenkinsProjectService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private GitLabService gitLabService;
    @Autowired
    private DeployApplyMapper deployApplyMapper;
    @Autowired
    private DeployProjectApplyMapper deployProjectApplyMapper;

    @RequestMapping
    public String index(Model model, @CurrentDeployEnvironment DeployEnvironment deployEnvironment) {
        JenkinsConfig jenkinsConfig = settingService.getJenkinsConfig(deployEnvironment);

        model.addAttribute("jenkinsConfig", jenkinsConfig);
        model.addAttribute("projects", jenkinsProjectMapper.findByEnvironments(deployEnvironment));
        return "project/index";
    }

    @RequestMapping(value = "apply", method = RequestMethod.POST)
    public String apply(@RequestParam("projects[]") List<String> projects, Model model, @CurrentUser User user, @CurrentDeployEnvironment DeployEnvironment deployEnvironment) {
        JenkinsAPI jenkinsAPI = jenkinsProjectService.buildJenkinsAPIFromEnvironment(deployEnvironment);
        GitLabAPI gitLabAPI = gitLabService.buildGitLabAPI(user);
        List<JenkinsProject> projectList = jenkinsProjectMapper.findByProjectNames(projects, deployEnvironment);

        List<ProjectApplyVO> projectApplyList = new ArrayList<>(projects.size());
        for (JenkinsProject project : projectList) {
            JenkinsBuildResult lastBuildResult = jenkinsAPI.getLastBuildResult(project.getName());
            String lastBuildVersion = jenkinsAPI.getLastBuildVersion(project.getName());
            GitLabMergeRequest lastMergeRequest = gitLabAPI.getProjectLastMergeRequest(project.getGitRepositoryPath());

            ProjectApplyVO projectApply = new ProjectApplyVO();
            projectApply.setName(project.getName());
            projectApply.setLastBuildResult(lastBuildResult);
            projectApply.setLastBuildVersion(lastBuildVersion);
            projectApply.setLastMergeRequest(lastMergeRequest);
            projectApplyList.add(projectApply);
        }

        model.addAttribute("projectApplyList", projectApplyList);
        return "project/apply";
    }

    @RequestMapping(value = "applySubmit", method = RequestMethod.POST)
    public String applySubmit(@RequestParam Map<String, String> params, @RequestParam MultiValueMap<String, String> multiValueMap, Model model, @CurrentUser User user, @CurrentDeployEnvironment DeployEnvironment deployEnvironment) {
        DeployApply deployApply = new DeployApply();
        deployApply.setDescription(params.get("发版内容简述"));
        deployApply.setUid(user.getUid());
        deployApply.setEnv(deployEnvironment);
        deployApplyMapper.save(deployApply);

        for (int i = 0; i < multiValueMap.get("项目名[]").size(); i++) {
            DeployProjectApply deployProjectApply = new DeployProjectApply();
            deployProjectApply.setDaId(deployApply.getId());
            deployProjectApply.setProject(multiValueMap.get("项目名[]").get(i));
            deployProjectApply.setVersion(multiValueMap.get("构建镜像版本[]").get(i));
            deployProjectApplyMapper.save(deployProjectApply);
        }

        model.addAttribute("message", "提交成功，请等待审批");
        return "site/message";
    }
}
