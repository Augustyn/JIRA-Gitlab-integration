package pl.hycom.jira.plugins.gitlab.integration.service.processors;

/**
 * Created by Tomek on 15/14/16.
 */
public interface SchedulerInterface {
    public void reschedule(long interval);
}
