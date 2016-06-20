package pl.hycom.jira.plugins.gitlab.integration.service;

import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by vagrant on 6/14/16.
 */
public interface CommitManager
{
    void updateCommitsForProject(int projectId) throws SQLException, ParseException, IOException;
    void updateCommitsForAll() throws SQLException, ParseException, IOException;
}
