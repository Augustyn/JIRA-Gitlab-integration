/*
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
package pl.hycom.jira.plugins.gitlab.integration.listeners;

import com.atlassian.sal.api.lifecycle.LifecycleAware;
import com.atlassian.scheduler.SchedulerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;


@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Inject)) //Inject all final variables.
public class Scheduler implements IScheduler, LifecycleAware {
    @Getter
    private static final String KEY = Scheduler.class.getName() + "instance";
    private static final String JOB_NAME = "Schedules info about commits"; // nazwa zadania
    private final SchedulerService pluginScheduler;
    private long interval = 300000L;
    @Setter
    private Date lastRun = null;

    public void onStart() {
        reschedule(interval);
    }

    public void onStop() {
    }

    public void reschedule(long interval) {
        this.interval = interval;

        HashMap params = new HashMap<String, Object>();
        params.put(KEY, Scheduler.this);


        //pluginScheduler.scheduleJob();

    }
}