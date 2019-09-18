package com.kelystor.boatcross.service;

import com.kelystor.boatcross.entity.GitLabConfig;
import com.kelystor.boatcross.entity.User;
import com.kelystor.boatcross.vendor.gitlab.GitLabAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitLabService {
    @Autowired
    private SettingService settingService;

    public GitLabAPI buildGitLabAPI(User user) {
        GitLabConfig gitLabConfig = settingService.getGitLabConfig();
        return GitLabAPI.connect(gitLabConfig.getUrl(), user.getGitlabPrivateToken());
    }
}
