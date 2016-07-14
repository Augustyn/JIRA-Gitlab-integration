package pl.hycom.jira.plugins.gitlab.integration.scheduler.impl;

import com.atlassian.scheduler.SchedulerService;
import com.atlassian.scheduler.SchedulerServiceException;
import com.atlassian.scheduler.config.*;
import com.atlassian.scheduler.status.JobDetails;
import lombok.extern.log4j.Log4j;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeException;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomePluginScheduleManager;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuff;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuffDao;

import java.util.TimeZone;

import static pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomePluginJobRunner.AWESOME_ID;
import static pl.hycom.jira.plugins.gitlab.integration.scheduler.impl.AwesomePluginScheduleManagerImpl.toJobId;

/**
 * Encapsulates the logic of setting up the example data and schedules.
 *
 * <p>Copyright (c) 2016, Authors</p>
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p>
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 * @since v1.0
 */
@Log4j
public class Example
{
    private static final long EXAMPLE_SCHEDULE_BASE_INTERVAL_MILLIS = 60000L;
    private static final int EXAMPLE_SCHEDULE_COUNT = 5;

    private final SchedulerService schedulerService;
    private final AwesomePluginScheduleManager awesomePluginScheduleManager;
    private final AwesomeStuffDao awesomeStuffDao;

    Example(SchedulerService schedulerService, AwesomePluginScheduleManager awesomePluginScheduleManager, AwesomeStuffDao awesomeStuffDao)
    {
        this.schedulerService = schedulerService;
        this.awesomePluginScheduleManager = awesomePluginScheduleManager;
        this.awesomeStuffDao = awesomeStuffDao;
    }

    /**
     * This is an example of creating a schedule only if it does not already exist.  This is simplest and
     * quickest unless you have a good reason to think that the existing schedule needs to be replaced,
     * such as if the schedule interval is being changed or you need to change what information is
     * stored in the parameter map.
     *
     * @param stuff something we'll be working with in the schedule
     * @param intervalInMillis how frequently to run
     */
    private void createScheduleIfAbsent(AwesomeStuff stuff, long intervalInMillis) throws AwesomeException
    {
        final JobDetails existing = schedulerService.getJobDetails(toJobId(stuff));
        if (existing == null)
        {
            log.info("Schedule for stuff id=" + stuff.getID() + " does not exist, so createScheduleIfAbsent will create it");
            awesomePluginScheduleManager.createAwesomeSchedule(stuff, intervalInMillis);
        }
        else if (existing.getParameters().get(AWESOME_ID) instanceof Long)
        {
            log.info("Schedule for id=" + stuff.getID() + " has a Long id instead of Integer.  Zapping it because it must be old...");
            awesomePluginScheduleManager.createAwesomeSchedule(stuff, intervalInMillis);
        }
        else
        {
            log.info("Schedule for stuff id=" + stuff
                    .getID() + " already exists, so createScheduleIfAbsent is not going to do anything: " + existing);
        }
    }


    // This will replace the existing schedule if it has to.
    private void createOrUpdateSchedule(AwesomeStuff stuff, long intervalInMillis) throws AwesomeException
    {
        final JobDetails existing = schedulerService.getJobDetails(toJobId(stuff));
        if (existing == null)
        {
            log.info("Schedule for stuff id=" + stuff.getID() + " does not exist, so createScheduleIfAbsent will create it normally");
        }
        else
        {
            log.info("Schedule for stuff id=" + stuff.getID() + " already exists, so createOrUpdateSchedule is removing the existing one first: " + existing);
        }
        awesomePluginScheduleManager.createAwesomeSchedule(stuff, intervalInMillis);
    }



    // In this example, we set up several jobs for the scheduler to trigger.  We create a few active objects, and
    // their IDs are multiplied by 1 minute to determine how frequently they will run.
    public void setUpExample()
    {
        try
        {
            log.info("Setting up example data...");
            for (int i=1; i<=EXAMPLE_SCHEDULE_COUNT; ++i)
            {
                AwesomeStuff stuff = awesomeStuffDao.findById(i);
                if (stuff == null)
                {
                    stuff = awesomeStuffDao.create("Awesome stuff #" + i);
                    log.info("Created: " + stuff);
                }
                else
                {
                    log.info("Reusing: " + stuff);
                }
                if ((i&1) == 0)
                {
                    // Even ones will replace an existing entry if there is one
                    createOrUpdateSchedule(stuff, EXAMPLE_SCHEDULE_BASE_INTERVAL_MILLIS * i);
                }
                else
                {
                    // Odd ones will create schedules that do not yet exist, but leave existing ones alone
                    createScheduleIfAbsent(stuff, EXAMPLE_SCHEDULE_BASE_INTERVAL_MILLIS * i);
                }
            }
        }
        catch (AwesomeException ae)
        {
            throw new RuntimeException("Unable to initialize example", ae);
        }



        // For fun, we also create a job with a JobRunnerKey that nobody is going to register.  This is to
        // simulate the case where the plugin which created the job has since been disabled or uninstalled,
        // so its JobRunner is no longer available.
        try
        {
            if (schedulerService.getJobDetails(JobId.of("Deliberately broken job")) == null)
            {
                schedulerService.scheduleJob(JobId.of("Deliberately broken job"),
                        JobConfig.forJobRunnerKey(JobRunnerKey.of("A job runner that will not get registered"))
                                 .withRunMode(RunMode.RUN_LOCALLY)
                                 .withSchedule(Schedule
                                         .forCronExpression("*/5 */7 * * * ?", TimeZone.getTimeZone("America/Chicago"))));
                // Plugins should not rely on actually getting exactly the same cron schedule that they submitted.
                // Expressions that could trigger more than once in a given minute, like this one, are not accepted
                // as-is.  The "*/5" above could cause it to trigger every 5 seconds while the other conditions
                // match, and instead the seconds field will be forced to a random value from 0 to 59 (inclusive),
                // which makes certain that it will match at most once in any given minute.  If the supplied cron
                // expression already uses an acceptable value, like "0" or "23", then it is accepted as-is.
                // The behavior applies to interval schedules as well -- they are quantized to multiples of
                // 60 seconds, rounding up if necessary.
                log.info("Created the deliberately broken job");
            }
            else
            {
                log.info("Broken job already exists; not setting it up...");
            }
        }
        catch (SchedulerServiceException sse)
        {
            log.error("Could not set up the deliberately broken job", sse);
        }
        log.info("Done setting up example schedules");
    }

}
