package pl.hycom.jira.plugins.gitlab.integration.scheduler.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;

import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeException;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomePluginScheduleManager;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuff;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.ExtraInfo;
import com.atlassian.scheduler.SchedulerService;
import com.atlassian.scheduler.SchedulerServiceException;
import com.atlassian.scheduler.config.JobConfig;
import com.atlassian.scheduler.config.JobId;
import com.atlassian.scheduler.config.RunMode;
import com.atlassian.scheduler.config.Schedule;
import com.atlassian.scheduler.status.JobDetails;

import com.google.common.collect.ImmutableMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomePluginJobRunner.AWESOME_ID;
import static pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomePluginJobRunner.AWESOME_JOB;
import static com.atlassian.scheduler.config.RunMode.RUN_LOCALLY;
import static com.atlassian.scheduler.config.RunMode.RUN_ONCE_PER_CLUSTER;

/**
 * @since v1.0
 */
public class AwesomePluginScheduleManagerImpl implements AwesomePluginScheduleManager
{
    private static final Logger LOG = LoggerFactory.getLogger(AwesomePluginScheduleManagerImpl.class);
    private static final Random RANDOM = new Random();

    // A minimum amount of time, in milliseconds, to wait before the job runs for the first time.  If you
    // are scheduling a RUN_ONCE_PER_CLUSTER job and both nodes start at once, there is a race condition
    // where both nodes can miss the fact that the other node is also scheduling the job.  If they both
    // try to schedule it at once, then it is possible for both nodes to successfully run the job.  Putting
    // in a small delay (15 seconds is recommended) before the job can possibly run for the first time
    // prevents this from happening because the second node will overwrite the first node's schedule before
    // it gets to run in all but the most extreme circumstances.  If your network latency is so high that
    // a 15 second delay is too long, then your cluster is likely to break for other reasons.
    private static final int MIN_DELAY = 15000;

    // The purpose of the jitter is to reduce the risk that your plugin will schedule itself to run at more
    // or less the same time as countless other plugin jobs do by waiting a random time period before the
    // first run.  This is important if your plugin is part of Atlassian's OnDemand offering, but not
    // necessary for other plugins.
    private static final int MAX_JITTER = 10000;

    // We will generate our own JobIds, both so that they will be meaningful and so that we can find them again
    // later.  This also makes it very unlikely that we would accidentally delete some other plugin's jobs,
    // because we are using our own class to namespace them.  Something like the plugin key would also make
    // sense.  If you are using Schedule.runOnce jobs, then you may be happy using an ID that is generated
    // for you using SchedulerService.scheduleJobWithGeneratedId.
    private static final String JOB_ID_PREFIX = "AwesomeJob for id=";



    private final SchedulerService schedulerService;

    public AwesomePluginScheduleManagerImpl(final SchedulerService schedulerService)
    {
        this.schedulerService = schedulerService;
    }

    @Override
    public void createAwesomeSchedule(@Nonnull AwesomeStuff stuff, long intervalInMillis) throws AwesomeException
    {
        final int jitter = RANDOM.nextInt(MAX_JITTER);
        final Date firstRun = new Date(System.currentTimeMillis() + MIN_DELAY + jitter);
        final Map<String, Serializable> parameters = ImmutableMap.of(
                AWESOME_ID, stuff.getID(),
                "extraInfo", ExtraInfo.random());

        // Just for the sake of illustration, we will change up with run mode we use.  A real plugin
        // obviously wouldn't do this.
        final RunMode runMode = (stuff.getID() & 2) == 0 ? RUN_LOCALLY : RUN_ONCE_PER_CLUSTER;
        final JobConfig jobConfig = JobConfig.forJobRunnerKey(AWESOME_JOB)
                                             .withSchedule(Schedule.forInterval(intervalInMillis, firstRun))
                                             .withRunMode(runMode)
                                             .withParameters(parameters);
        LOG.info("Scheduling job with jitter=" + jitter + ": " + jobConfig);

        try
        {
            final JobId jobId = toJobId(stuff);
            final JobDetails existing = schedulerService.getJobDetails(jobId);
            if (existing != null)
            {
                LOG.info("We will be replacing an existing job with jobId=" + jobId + ": " + existing);
                // Note that we don't need to delete the existing job first; scheduleJob will replace the previous one
                // deleteAwesomeSchedule(existing);
            }

            schedulerService.scheduleJob(jobId, jobConfig);
            LOG.info("Successfully scheduled jobId=" + jobId);
        }
        catch (SchedulerServiceException sse)
        {
            throw new AwesomeException("Unable to create schedule for awesome stuff '" + stuff.getName() + '\'', sse);
        }
    }

    @Override
    public void deleteAwesomeSchedule(int awesomeId) throws AwesomeException
    {
        final JobId id = toJobId(awesomeId);
        final JobDetails jobDetails = schedulerService.getJobDetails(id);

        // Some plugins may prefer to throw an exception if you attempt to delete a schedule that
        // does not exist, but idempotency is more polite.
        if (jobDetails != null)
        {
            deleteAwesomeSchedule(jobDetails);
        }
    }

    private void deleteAwesomeSchedule(@Nonnull JobDetails jobDetails) throws AwesomeException
    {
        // Why did we get asked to delete somebody else's job?!  Calculating the jobId directly
        // would probably be less trouble.
        if (!AWESOME_JOB.equals(jobDetails.getJobRunnerKey()))
        {
            throw new AwesomeException("JobId '" + jobDetails.getJobId() + "' does not belong to me!");
        }
        schedulerService.unscheduleJob(jobDetails.getJobId());
    }




    static JobId toJobId(AwesomeStuff stuff)
    {
        return toJobId(stuff.getID());
    }

    static JobId toJobId(int awesomeId)
    {
        return JobId.of(JOB_ID_PREFIX + awesomeId);
    }
}


