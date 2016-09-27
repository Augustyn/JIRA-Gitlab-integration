package it.pl.hycom.jira.plugins.gitlab.integration.dao;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugins.osgi.test.AtlassianPluginsTestRunner;
import net.java.ao.EntityManager;
import net.java.ao.test.jdbc.DatabaseUpdater;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigEntity;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigManagerDao;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
 *
 */
@RunWith(com.atlassian.plugins.osgi.test.AtlassianPluginsTestRunner.class)
public class ConfigManagerDaoTest {
    @Autowired
    private ConfigManagerDao configManager;

    @Autowired
    private ActiveObjects entityManager;


    @Before
    public void setup() {
        System.setProperty("baseurl","http://localhost:2990/jira");
    }


    @Test
    public void allInOneTest() throws SQLException {
        Long testProjectId = 9999L;
        assertNotNull(configManager);
        assertNotNull(entityManager);


        ConfigEntity saved = configManager.updateProjectConfig(testProjectId,"testlink","testsecret","testid","testname");

        ConfigEntity retrieved = configManager.getProjectConfig(testProjectId);


        assertEquals( testProjectId,retrieved.getProjectID());
        assertEquals( "testlink", retrieved.getLink());
        assertEquals( "testsecret",retrieved.getSecret());
        assertEquals( "testid",retrieved.getClientId());

        assertEquals( saved.getLink(), retrieved.getLink());
        assertEquals( saved.getSecret(),retrieved.getSecret());
        assertEquals( saved.getClientId(),retrieved.getClientId());

    }


    public static class ConfigManagerDaoTestDatabaseUpdater implements DatabaseUpdater
    {

        @Override
        public void update(EntityManager em) throws Exception {
            em.migrate(ConfigEntity.class);
        }
    }

}
