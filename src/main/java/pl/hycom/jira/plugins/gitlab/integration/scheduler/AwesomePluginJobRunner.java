package pl.hycom.jira.plugins.gitlab.integration.scheduler;

import com.atlassian.scheduler.JobRunner;
import com.atlassian.scheduler.config.JobRunnerKey;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.impl.AwesomePluginJobRunnerImpl;

/**
 * @since v1.0
 */
public interface AwesomePluginJobRunner extends JobRunner
{
    /** Our job runner key */
    JobRunnerKey AWESOME_JOB = JobRunnerKey.of(AwesomePluginJobRunnerImpl.class.getName());

    /** Name of the parameter map entry where the ID is stored */
    String AWESOME_ID = "awesomeId";
}
