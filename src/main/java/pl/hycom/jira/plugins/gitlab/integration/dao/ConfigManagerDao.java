package pl.hycom.jira.plugins.gitlab.integration.dao;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by vagrant on 5/17/16.
 */

public interface ConfigManagerDao
{
    ConfigEntity getProjectConfig(int projectID) throws SQLException;
    public List<ConfigEntity> getAllProjectConfigs() throws SQLException;
    ConfigEntity updateProjectConfig(int projectID,String gitlabLink,String gitlabSecret,String gitlabClientId,String gitlabProjectName) throws SQLException;
    public void updateGitlabProjectId(int projectID, int gitlabProjectID);
}
