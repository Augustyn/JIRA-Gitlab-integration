package pl.hycom.jira.plugins.gitlab.integration.listeners;

import com.atlassian.sal.api.scheduling.PluginJob;
import com.atlassian.sal.api.scheduling.PluginScheduler;
import com.atlassian.sal.api.lifecycle.LifecycleAware;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import pl.hycom.jira.plugins.gitlab.integration.service.CommitService;
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
    private PluginScheduler pluginScheduler;
    private long interval = 300000L;
    @Setter
    private Date lastRun = null;

    public void onStart() {
        reschedule(interval);
    }

    public void onStop(){
    }

     public void reschedule(long interval) {
        this.interval = interval;

         HashMap params = new HashMap<String,Object>();
         params.put(KEY, Scheduler.this);

         pluginScheduler.scheduleJob(
                JOB_NAME,             // unikalna nazwa zadania do wykonania
                CommitService.class,     // klasa zadania do wykonania
                params,
                new Date(),                 // czas kiedy praca jest rozpoczynana
                interval);                  // co jaki czas w milisekundach zadanie bedzie wykonywane
        log.info(String.format("Informacje o comittach sa pobierane co %dms", interval));
    }


}
