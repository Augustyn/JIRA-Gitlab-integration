/*
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
package pl.hycom.jira.plugins.gitlab.integration.dao;

import com.atlassian.activeobjects.external.ActiveObjects;
import lombok.RequiredArgsConstructor;
import net.java.ao.DBParam;
import net.java.ao.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Repository
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject)) //Inject all final variables.
public class ConfigManagerDaoImpl implements ConfigManagerDao {

    private final ActiveObjects entityManager;

    @Nullable
    public ConfigEntity getProjectConfig(Long projectID) throws SQLException {
        return entityManager.get(ConfigEntity.class, projectID);    //returns null if no entities exist
    }

    public List<ConfigEntity> getAllProjectConfigs() throws SQLException {
        ConfigEntity[] configs = entityManager.find(ConfigEntity.class);
        return Arrays.asList(configs);
    }
    @Nullable
    public ConfigEntity updateProjectConfig(Long projectID, String gitlabLink, String gitlabSecret, String gitlabClientId,
                                            String gitlabProjectName) throws SQLException {
        ConfigEntity projectConfig;
        if(entityManager.count(ConfigEntity.class, Query.select().where("PROJECT_ID LIKE ?", projectID)) > 0 ) {
            projectConfig = entityManager.get(ConfigEntity.class, projectID);
        } else {
            projectConfig = entityManager.create(ConfigEntity.class,new DBParam("PROJECT_ID", projectID));
        }
        if (projectConfig == null) {
            return null;
        }
        projectConfig.setGitlabURL(gitlabLink);
        projectConfig.setGitlabSecretToken(gitlabSecret);
        projectConfig.setClientId(gitlabClientId);
        projectConfig.setGitlabProjectName(gitlabProjectName);

        projectConfig.save();
        return projectConfig;
    }

    public void updateGitlabProjectId(Long projectID, Long gitlabProjectID) {
        ConfigEntity projectConfig = entityManager.get(ConfigEntity.class, projectID);
        projectConfig.setGitlabProjectId(gitlabProjectID);
        projectConfig.save();
    }
}
