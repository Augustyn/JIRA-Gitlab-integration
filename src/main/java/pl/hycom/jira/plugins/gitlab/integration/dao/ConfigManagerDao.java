package pl.hycom.jira.plugins.gitlab.integration.dao;

import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by vagrant on 5/17/16.
 */

@Service
public interface ConfigManagerDao
{
    ConfigEntity getProjectConfig(int projectID) throws SQLException;
    ConfigEntity updateProjectConfig(int projectID,String gitlabLink,String gitlabSecret,String gitlabClientId) throws SQLException;
}
