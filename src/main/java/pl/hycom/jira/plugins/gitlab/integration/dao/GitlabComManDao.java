package pl.hycom.jira.plugins.gitlab.integration.dao;

/*
 * <p>Copyright (c) 2016, Authors
 * Project:  gitlab-integration.</p>
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.hycom.jira.plugins.gitlab.integration.model.GitlabProject;

import java.sql.SQLException;
import java.util.List;


@Repository
public class GitlabComManDao implements IGitlabComManDao
{
    @Autowired
    private GitlabComDao gitlabComDao;
    @Autowired
    private ConfigManagerDao configManagerDao;

    @Override
    public int findGitlabProjectId(int jiraProjectId) throws SQLException
    {
        int gitlabProjectId=-1;
        ConfigEntity configEntity = configManagerDao.getProjectConfig(jiraProjectId);

        List<GitlabProject> gitlabProjectList = gitlabComDao.getGitlabProjects(configEntity);

        for(GitlabProject project : gitlabProjectList){
            if(project.getGitlabProjectName().equalsIgnoreCase(configEntity.getGitlabProjectName())){
                gitlabProjectId = project.getGitlabProjectId();
            }
        }
        configManagerDao.updateGitlabProjectId(jiraProjectId,gitlabProjectId);
        return gitlabProjectId;
    }
}
