/*
 *
 * <p>Copyright (c) 2016. Authors</p>
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p>
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *limitations under the License.</p>
 *
 */

package pl.hycom.jira.plugins.gitlab.integration.service.impl;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigEntity;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigManagerDao;
import pl.hycom.jira.plugins.gitlab.integration.dao.IGitlabCommunicationDao;
import pl.hycom.jira.plugins.gitlab.integration.model.GitlabProject;
import pl.hycom.jira.plugins.gitlab.integration.service.GitlabService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author higashi on 2016-11-13, HYCOM S.A.
 */
@Log4j
@Service
@NoArgsConstructor
public class GitlabServiceImpl implements GitlabService {
    @Autowired private ConfigManagerDao configManager;
    @Autowired private IGitlabCommunicationDao gitlabCommunicationManagerDao;

    @Override
    public Optional<GitlabProject> getGitlabProject(ConfigEntity config) throws IOException {
        List<GitlabProject> gitlabProjects = gitlabCommunicationManagerDao.getGitlabProjects(config);
        final Optional<GitlabProject> project = gitlabProjects.stream().filter(p -> p.getGitlabProjectName().equalsIgnoreCase(config.getGitlabProjectName())).findAny();
        project.ifPresent(gitlabProject -> configManager.updateGitlabProjectId(config.getJiraProjectID(), gitlabProject.getGitlabProjectId()));
        return project;
    }
}
