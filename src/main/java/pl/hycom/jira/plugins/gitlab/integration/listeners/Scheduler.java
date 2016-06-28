package pl.hycom.jira.plugins.gitlab.integration.listeners;


import com.atlassian.sal.api.lifecycle.LifecycleAware;
import com.atlassian.scheduler.SchedulerService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;


/**
 * Created by Tomek on 15/14/16.
 */

@Log4j
public class Scheduler implements IScheduler, LifecycleAware {
    @Getter
    private static final String KEY = Scheduler.class.getName() + "instance";
    private static final String JOB_NAME = "Schedules info about commits"; // nazwa zadania
    @Autowired
    private SchedulerService pluginScheduler;
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