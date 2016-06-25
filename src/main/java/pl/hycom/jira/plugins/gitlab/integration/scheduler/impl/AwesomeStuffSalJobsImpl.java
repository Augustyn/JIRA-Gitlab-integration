package pl.hycom.jira.plugins.gitlab.integration.scheduler.impl;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.extern.log4j.Log4j;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuffDao;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuffSalJobs;
import com.atlassian.sal.api.scheduling.PluginScheduler;

import com.google.common.collect.ImmutableMap;

@Log4j
public class AwesomeStuffSalJobsImpl implements AwesomeStuffSalJobs
{
    static final String KEY = AwesomeStuffSalJobsImpl.class.getName() + ":instance";
    public static final String JOB_NAME = AwesomeStuffSalJobsImpl.class.getName() + ":job";

    private final AtomicBoolean scheduled = new AtomicBoolean();
    private final PluginScheduler pluginScheduler;  // provided by SAL
    private final AwesomeStuffDao awesomeStuffDao;

    public AwesomeStuffSalJobsImpl(PluginScheduler pluginScheduler, AwesomeStuffDao awesomeStuffDao)
    {
        this.pluginScheduler = pluginScheduler;
        this.awesomeStuffDao = awesomeStuffDao;
    }



    public void reschedule(int intervalInSeconds)
    {
        Map<String, Object> jobDataMap = ImmutableMap.of(KEY, (Object) AwesomeStuffSalJobsImpl.this);
        pluginScheduler.scheduleJob(
                JOB_NAME,                    // unique name of the job
                AwesomeStuffSalJob.class,    // class of the job
                jobDataMap,                  // key and class of the job to start
                new Date(),                  // the time the job is to start
                intervalInSeconds * 1000L);  // interval between repeats, in milliseconds
        scheduled.set(true);
        log.info(String.format("Task monitor scheduled to run every %d seconds.", intervalInSeconds));
    }

    public void unschedule()
    {
        try
        {
            if (scheduled.getAndSet(false))
            {
                pluginScheduler.unscheduleJob(JOB_NAME);
            }
        }
        catch (IllegalArgumentException iae)
        {
            log.warn("Looks like the job was not scheduled, after all", iae);
        }
    }

    AwesomeStuffDao getAwesomeStuffDao()
    {
        return awesomeStuffDao;
    }
}
