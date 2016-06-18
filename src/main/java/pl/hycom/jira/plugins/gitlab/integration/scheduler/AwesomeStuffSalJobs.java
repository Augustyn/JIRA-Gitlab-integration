package pl.hycom.jira.plugins.gitlab.integration.scheduler;

public interface AwesomeStuffSalJobs
{
    int DEFAULT_INTERVAL_IN_SECONDS = 60;

    void reschedule(int intervalInSeconds);

    void unschedule();
}
