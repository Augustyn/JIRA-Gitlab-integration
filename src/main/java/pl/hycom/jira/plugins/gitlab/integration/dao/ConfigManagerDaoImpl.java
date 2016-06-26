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

import com.atlassian.activeobjects.external.ActiveObjects;
import net.java.ao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Service
public class ConfigManagerDaoImpl implements ConfigManagerDao
{
    @Autowired
    private ActiveObjects entityManager;

    public ConfigEntity getProjectConfig(int projectID) throws SQLException
    {
        return entityManager.get(ConfigEntity.class,projectID);    //returns null if no entities exist
    }

    public List<ConfigEntity> getAllProjectConfigs() throws SQLException
    {
        ArrayList<ConfigEntity> result = new ArrayList<ConfigEntity>();
        ConfigEntity configs[] = entityManager.find(ConfigEntity.class);
        for(ConfigEntity conf : configs){
            result.add(conf);
        }
        return result;
    }


    public ConfigEntity updateProjectConfig(int projectID,String gitlabLink,String gitlabSecret,String gitlabClientId) throws SQLException
    {
        ConfigEntity projectConfig;
        if( entityManager.count(ConfigEntity.class, Query.select().where("PROJECT_ID LIKE ?",projectID)) > 0 )
        {
            projectConfig = entityManager.get(ConfigEntity.class,projectID);
        }
        else
        {
            projectConfig = entityManager.create(ConfigEntity.class,new DBParam("PROJECT_ID",projectID));
        }

        projectConfig.setLink(gitlabLink);
        projectConfig.setSecret(gitlabSecret);
        projectConfig.setClientId(gitlabClientId);

        projectConfig.save();
        return  projectConfig;
    }


}
