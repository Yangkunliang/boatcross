package com.kelystor.boatcross.service;

import com.kelystor.boatcross.dao.SettingMapper;
import com.kelystor.boatcross.entity.AliyunContainerConfig;
import com.kelystor.boatcross.entity.GitLabConfig;
import com.kelystor.boatcross.entity.JenkinsConfig;
import com.kelystor.boatcross.entity.Setting;
import com.kelystor.boatcross.enums.DeployEnvironment;
import com.kelystor.boatcross.mapper.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingService {
    public static final String JENKINS_KEY = "jenkins";
    public static final String GITLAB_KEY = "gitlab";
    public static final String ALIYUN_CONTAINER_KEY = "aliyunContainer";

    @Autowired
    private SettingMapper settingMapper;
    @Autowired
    private JsonMapper jsonMapper;

    public JenkinsConfig getJenkinsConfig(DeployEnvironment environment) {
        Optional<Setting> setting = settingMapper.findByKeyAndEnvironment(JENKINS_KEY, environment);
        return setting.map(settingValue -> jsonMapper.toObject(settingValue.getValue(), JenkinsConfig.class)).orElse(null);
    }

    public GitLabConfig getGitLabConfig() {
        Optional<Setting> setting = settingMapper.findByKeyAndEnvironment(GITLAB_KEY, null);
        return setting.map(settingValue -> jsonMapper.toObject(settingValue.getValue(), GitLabConfig.class)).orElse(null);
    }

    public AliyunContainerConfig getAliyunContainerConfig(DeployEnvironment environment) {
        Optional<Setting> setting = settingMapper.findByKeyAndEnvironment(ALIYUN_CONTAINER_KEY, environment);
        return setting.map(settingValue -> jsonMapper.toObject(settingValue.getValue(), AliyunContainerConfig.class)).orElse(null);
    }

    public void update(String configKey, Object object, DeployEnvironment environment) {
        Setting setting = new Setting();
        setting.setKey(configKey);
        setting.setValue(jsonMapper.toJson(object));
        setting.setEnv(environment);

        if (settingMapper.update(setting) <= 0) {
            settingMapper.save(setting);
        }
    }
}
