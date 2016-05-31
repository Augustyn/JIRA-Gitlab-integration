package pl.hycom.jira.plugins.gitlab.integration.dao;

import java.sql.SQLException;

/**
 * Created by vagrant on 5/17/16.
 */
public interface ConfigManagerDao
{
    ConfigEntity getProjectConfig(int projectID) throws SQLException;
    void updateProjectConfig(int projectID,String gitlabLink,String gitlabSecret,String gitlabClientId) throws SQLException;
}
