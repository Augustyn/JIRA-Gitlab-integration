package pl.hycom.jira.plugins.gitlab.integration.service;


import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigEntity;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigManagerDao;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Component
public class CommitManagerImpl implements CommitManager
{
    @Autowired
    private ConfigManagerDao configManager;
    @Autowired
    private CommitService commitService;
    @Autowired
    private ProcessorManager processorManager;
    @Autowired
    private CommitMessageParser commitMessageParser;


    @Override
    public void updateCommitsForProject(int projectId) throws SQLException, ParseException, IOException
    {
        ConfigEntity config = configManager.getProjectConfig(projectId);
        List<Commit> commitList = commitService.getNewCommits( (long) projectId);

        for (Commit c : commitList){
            String issue = commitMessageParser.findIssue(c,projectId);
            c.setIssueKey(issue);
        }

        processorManager.startProcessors(commitList);
    }

    @Override
    public void updateCommitsForAll() throws SQLException, ParseException, IOException
    {
        List<ConfigEntity> configList = configManager.getAllProjectConfigs();
        for (ConfigEntity config : configList){
            updateCommitsForProject(config.getProjectID());
        }

    }
}
