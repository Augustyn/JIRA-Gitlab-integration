package pl.hycom.jira.plugins.gitlab.integration.scheduler.action;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.scheduler.SchedulerHistoryService;
import com.atlassian.scheduler.SchedulerRuntimeException;
import com.atlassian.scheduler.SchedulerService;
import com.atlassian.scheduler.config.JobId;
import com.atlassian.scheduler.config.JobRunnerKey;
import com.atlassian.scheduler.status.JobDetails;
import com.atlassian.scheduler.status.RunDetails;
import com.opensymphony.util.TextUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Simple webwork action for gathering info about the scheduler for a debugging display.
 *
 * @since v1.0
 */
public class ShowSchedulerInfo extends JiraWebActionSupport
{

    private final SchedulerService schedulerService;
    private final SchedulerHistoryService schedulerHistoryService;

    private Set<JobRunnerKey> registeredJobRunnerKeys;
    private Set<JobRunnerKey> allJobRunnerKeys;
    private String remove;

    public ShowSchedulerInfo(final SchedulerService schedulerService, final SchedulerHistoryService schedulerHistoryService)
    {
        this.schedulerService = schedulerService;
        this.schedulerHistoryService = schedulerHistoryService;
    }

    public Set<JobRunnerKey> getRegisteredJobRunnerKeys()
    {
        if (registeredJobRunnerKeys == null)
        {
            registeredJobRunnerKeys = new TreeSet<JobRunnerKey>(schedulerService.getRegisteredJobRunnerKeys());
        }
        return registeredJobRunnerKeys;
    }

    public Set<JobRunnerKey> getAllJobRunnerKeys()
    {
        if (allJobRunnerKeys == null)
        {
            allJobRunnerKeys = new TreeSet<JobRunnerKey>(schedulerService.getJobRunnerKeysForAllScheduledJobs());
            allJobRunnerKeys.addAll(getRegisteredJobRunnerKeys());
        }
        return allJobRunnerKeys;
    }

    public boolean isRegistered(JobRunnerKey jobRunnerKey)
    {
        return getRegisteredJobRunnerKeys().contains(jobRunnerKey);
    }

    public String getParameters(JobDetails jobDetails)
    {
        try
        {
            return String.valueOf(jobDetails.getParameters());
        }
        catch (SchedulerRuntimeException sre)
        {
            return String.valueOf(sre.getCause());
        }
    }

    public RunDetails getLastRun(JobId jobId)
    {
        return schedulerHistoryService.getLastRunForJob(jobId);
    }

    public List<JobDetails> getJobs(JobRunnerKey jobRunnerKey)
    {
        return schedulerService.getJobsByJobRunnerKey(jobRunnerKey);
    }

    public boolean handleRemove()
    {
        if (!TextUtils.stringSet(remove))
        {
            remove = null;
            return false;
        }

        final JobRunnerKey jobRunnerKey = JobRunnerKey.of(remove);
        if (getRegisteredJobRunnerKeys().contains(jobRunnerKey))
        {
            addErrorMessage("Job runner key '" + jobRunnerKey + "' is registered; you can only remove abandoned jobs, here.");
            return false;
        }

        final Collection<JobDetails> jobs = schedulerService.getJobsByJobRunnerKey(jobRunnerKey);
        if (jobs.isEmpty())
        {
            addErrorMessage("There do not seem to be any abandoned jobs using the key '" + jobRunnerKey + "', so there is nothing to remove.");
            return false;
        }

        for (JobDetails job : jobs)
        {
            try
            {
                schedulerService.unscheduleJob(job.getJobId());
            }
            catch (SchedulerRuntimeException sre)
            {
                addErrorMessage("Failed to remove job '" + job.getJobId() + "': " + sre);
            }
        }
        return !hasAnyErrors();
    }

    public String getRemove()
    {
        return remove;
    }

    public void setRemove(String remove)
    {
        this.remove = remove;
    }
}
