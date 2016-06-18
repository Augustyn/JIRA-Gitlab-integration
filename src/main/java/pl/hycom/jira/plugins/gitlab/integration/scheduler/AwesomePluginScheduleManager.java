package pl.hycom.jira.plugins.gitlab.integration.scheduler;

import javax.annotation.Nonnull;

/**
 * Creates or deletes a schedule for awesome stuff.
 *
 * @since v1.0
 */
public interface AwesomePluginScheduleManager
{
    void createAwesomeSchedule(@Nonnull AwesomeStuff stuff, long intervalInMillis) throws AwesomeException;

    void deleteAwesomeSchedule(int awesomeId) throws AwesomeException;
}
