package pl.hycom.jira.plugins.gitlab.integration.service.processors;

import com.atlassian.sal.api.scheduling.PluginScheduler;
import org.apache.log4j.Logger;
import com.atlassian.sal.api.lifecycle.LifecycleAware;
import pl.hycom.jira.plugins.gitlab.integration.model.CommitData;

import java.util.Date;
import java.util.HashMap;


/**
 * Created by Tomek on 15/14/16.
 */
public class SchedulerProcessor implements SchedulerInterface, LifecycleAware {

    static final String KEY = SchedulerProcessor.class.getName() + "instance";
    private static final String JOB_NAME = "job"; // nazwa zadania
    private final Logger logger = Logger.getLogger(IssueCommitChangeNotificationProcessor.class);
    private final PluginScheduler pluginScheduler;
    private long interval = 5000L;

    public SchedulerProcessor(PluginScheduler pluginScheduler) {
        this.pluginScheduler = pluginScheduler;
    }

    public void onStart() {
        reschedule(interval);
    }

    public void onStop(){
    }

    public void reschedule(long interval) {
        this.interval = interval;

        pluginScheduler.scheduleJob(
                JOB_NAME,             // unikalna nazwa zadania do wykonania
                CommitData.class,     // klasa zadania do wykonania
                new HashMap<String,Object>() {{
                    put(KEY, SchedulerProcessor.this);
                }},
                new Date(),                 // czas kiedy praca jest rozpoczynana
                interval);                  // co jaki czas w milisekundach zadanie bedzie wykonywane
        logger.info(String.format("Informacje o comittach sa pobierane co %dms", interval));
    }

}
