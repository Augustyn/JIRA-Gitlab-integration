package pl.hycom.jira.plugins.gitlab.integration.model;

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

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettings.*;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import net.java.ao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.sql.SQLException;



@Repository
public class ConfigManagerDao
{
    @Autowired
    private EntityManager entityManager;

    public ProjectConfigEntity getProjectConfig(String projectName) throws SQLException
    {
        return entityManager.get(ProjectConfigEntity.class,projectName);    //returns null if no entities exist
    }

    public void updateProjectConfig(String projectName,String gitlabLink,String gitlabSecret,String gitlabClientId) throws SQLException
    {
        ProjectConfigEntity projectConfig;
        if( entityManager.count(ProjectConfigEntity.class, Query.select().where("ProjectName LIKE ?",projectName))==0 )
        {

            projectConfig = entityManager.create(ProjectConfigEntity.class,new DBParam("ProjectName",projectName));
        }
        else
        {
            projectConfig = entityManager.get(ProjectConfigEntity.class,projectName);
        }

        projectConfig.setLink(gitlabLink);
        projectConfig.setSecret(gitlabSecret);
        projectConfig.setClientId(gitlabClientId);
    }

}
