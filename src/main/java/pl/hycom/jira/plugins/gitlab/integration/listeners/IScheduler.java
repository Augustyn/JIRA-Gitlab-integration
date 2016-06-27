package pl.hycom.jira.plugins.gitlab.integration.listeners;

/**
 * Created by Tomek on 15/14/16.
 */
public interface IScheduler {
    public void reschedule(long interval);
}
