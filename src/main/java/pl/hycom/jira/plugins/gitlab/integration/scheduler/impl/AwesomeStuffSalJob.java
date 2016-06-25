package pl.hycom.jira.plugins.gitlab.integration.scheduler.impl;

import java.util.Map;

import lombok.extern.log4j.Log4j;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeException;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuff;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuffDao;
import com.atlassian.sal.api.scheduling.PluginJob;

import static com.atlassian.jira.util.dbc.Assertions.notNull;

@Log4j
public class AwesomeStuffSalJob implements PluginJob
{

    public void execute(Map<String, Object> jobDataMap)
    {
        final AwesomeStuffSalJobsImpl monitor = (AwesomeStuffSalJobsImpl)jobDataMap.get(AwesomeStuffSalJobsImpl.KEY);
        notNull("monitor", monitor);
        AwesomeStuffDao awesomeStuffDao = monitor.getAwesomeStuffDao();
        notNull("awesomeStuffDao", awesomeStuffDao);

        log.info("Checking our awesome stuff...");
        try
        {
            AwesomeStuff[] awesomeStuffs = awesomeStuffDao.findByAll();
            log.info("We've got " + awesomeStuffs.length + " awesome stuff...");
        }
        catch (AwesomeException e)
        {
            log.error("Error retrieving all the awesome stuff: " + e.getMessage(), e);
        }
    }
}
