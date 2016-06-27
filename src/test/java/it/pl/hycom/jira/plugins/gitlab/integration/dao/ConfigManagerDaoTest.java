package it.pl.hycom.jira.plugins.gitlab.integration.dao;

/**
 * Created by vagrant on 5/16/16.
 */

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

@Component
@RunWith(AtlassianPluginsTestRunner.class)
public class ConfigManagerDaoTest
{
    @Autowired
    private ConfigManagerDao configManager;

    @Autowired
    private ActiveObjects entityManager;


    @Before
    public void setup(){

    }


    @Test
    public void allInOneTest() throws SQLException
    {
        int testProjectId = 9999;
        assertNotNull(configManager);
        assertNotNull(entityManager);


        ConfigEntity saved = configManager.updateProjectConfig(testProjectId,"testlink","testsecret","testid","testname");

        ConfigEntity retrieved = configManager.getProjectConfig(testProjectId);


        assertEquals( (Integer) testProjectId,retrieved.getProjectID());
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
