package pl.hycom.jira.plugins.gitlab.integration.scheduler.impl;

import java.util.Map;

import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeException;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuff;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuffDao;
import com.atlassian.sal.api.scheduling.PluginJob;

import org.slf4j.LoggerFactory;

import static com.atlassian.jira.util.dbc.Assertions.notNull;

public class AwesomeStuffSalJob implements PluginJob
{
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AwesomeStuffSalJob.class);

    public void execute(Map<String, Object> jobDataMap)
    {
        final AwesomeStuffSalJobsImpl monitor = (AwesomeStuffSalJobsImpl)jobDataMap.get(AwesomeStuffSalJobsImpl.KEY);
        notNull("monitor", monitor);
        AwesomeStuffDao awesomeStuffDao = monitor.getAwesomeStuffDao();
        notNull("awesomeStuffDao", awesomeStuffDao);

        LOG.info("Checking our awesome stuff...");
        try
        {
            AwesomeStuff[] awesomeStuffs = awesomeStuffDao.findByAll();
            LOG.info("We've got " + awesomeStuffs.length + " awesome stuff...");
        }
        catch (AwesomeException e)
        {
            LOG.error("Error retrieving all the awesome stuff: " + e.getMessage(), e);
        }
    }
}
