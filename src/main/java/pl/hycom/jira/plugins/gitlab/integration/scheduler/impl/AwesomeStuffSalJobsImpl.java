package pl.hycom.jira.plugins.gitlab.integration.scheduler.impl;

import com.atlassian.sal.api.scheduling.PluginScheduler;
import com.google.common.collect.ImmutableMap;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuffDao;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuffSalJobs;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
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
 */
@Log4j
@Component
public class AwesomeStuffSalJobsImpl implements AwesomeStuffSalJobs {
    static final String KEY = AwesomeStuffSalJobsImpl.class.getName() + ":instance";
    public static final String JOB_NAME = AwesomeStuffSalJobsImpl.class.getName() + ":job";

    private final AtomicBoolean scheduled = new AtomicBoolean();
    @Autowired
    private PluginScheduler pluginScheduler;  // provided by SAL
    @Autowired
    private AwesomeStuffDao awesomeStuffDao;

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
