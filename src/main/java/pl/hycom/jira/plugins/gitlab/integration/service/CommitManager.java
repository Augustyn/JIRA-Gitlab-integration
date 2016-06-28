package pl.hycom.jira.plugins.gitlab.integration.service;

import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.sql.SQLException;


public interface CommitManager
{
    void updateCommitsForProject(int projectId) throws SQLException, ParseException, IOException;
    void updateCommitsForAll() throws SQLException, ParseException, IOException;
}
