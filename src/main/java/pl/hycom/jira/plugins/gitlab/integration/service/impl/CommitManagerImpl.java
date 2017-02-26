package pl.hycom.jira.plugins.gitlab.integration.service.impl;
/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.project.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigEntity;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigManagerDao;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.search.CommitIndex;
import pl.hycom.jira.plugins.gitlab.integration.service.CommitManager;
import pl.hycom.jira.plugins.gitlab.integration.service.ProcessorManager;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Inject)) //Inject all final variables.
public class CommitManagerImpl implements CommitManager {
    private final ConfigManagerDao configManager;
    private final CommitService commitService;
    private final ProcessorManager processorManager;
    private final CommitIndex commitIndex;

    @Override
    public void updateCommitsForProject(ConfigEntity config) throws SQLException, IOException {
        List<Commit> commitList = commitService.getNewCommits(config);
        Pattern pattern = this.getPattern(config.getJiraProjectID());
        commitList.forEach( c-> c.setIssueKey(this.findIssueKey(c, pattern)));
        processorManager.startProcessors(commitList);
    }

    @Override
    public void updateCommitsForAll() throws SQLException, IOException {
        List<ConfigEntity> configList = configManager.getAllProjectConfigs();
        for (ConfigEntity config : configList){
            updateCommitsForProject(config);
        }
        commitIndex.commit();
    }

    @Override
    public String findIssueKey(Commit commit, Pattern pattern) {
        String commitMessage = commit.getMessage();
        Matcher matcher = pattern.matcher(commitMessage);
        return matcher.find() ? matcher.group(1) : "";
    }

    private Pattern getPattern(Long projectID) {
        Project currentProject = ComponentAccessor.getProjectManager().getProjectObj(projectID);
        String currentProjectKey = currentProject.getKey();
        return Pattern.compile("("+currentProjectKey + "-\\d+)");
    }
}
