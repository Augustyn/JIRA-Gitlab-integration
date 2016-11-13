package pl.hycom.jira.plugins.gitlab.integration.service.impl;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigEntity;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigManagerDao;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.service.CommitManager;
import pl.hycom.jira.plugins.gitlab.integration.service.ProcessorManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Copyright (c) 2016, Authors</p>
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
 * limitations under the License.</p>
 */
@Component
public class CommitManagerImpl implements CommitManager {
    @Autowired
    private ConfigManagerDao configManager;
    @Autowired
    private CommitService commitService;
    @Autowired
    private ProcessorManager processorManager;

    @Override
    public void updateCommitsForProject(Long projectId) throws SQLException, IOException {
        ConfigEntity config = configManager.getProjectConfig(projectId);
        List<Commit> commitList = commitService.getNewCommits( (long) projectId);

        for (Commit c : commitList){
            String issue = this.findIssue(c,projectId);
            c.setIssueKey(issue);
        }

        processorManager.startProcessors(commitList);
    }

    @Override
    public void updateCommitsForAll() throws SQLException, IOException {
        List<ConfigEntity> configList = configManager.getAllProjectConfigs();
        for (ConfigEntity config : configList){
            updateCommitsForProject(config.getProjectID());
        }
    }

    @Override
    public String findIssue(Commit commit, Long projectID)
    {
        Project currentProject = ComponentAccessor.getProjectManager().getProjectObj(projectID);
        String currentProjectKey = currentProject.getKey();
        String commitMessage = commit.getMessage();

        Pattern pattern = Pattern.compile("\\[" + currentProjectKey + "-(\\d+)");
        Matcher matcher = pattern.matcher(commitMessage);

        String issueIdString = matcher.group(1);

        return issueIdString;
    }
}
