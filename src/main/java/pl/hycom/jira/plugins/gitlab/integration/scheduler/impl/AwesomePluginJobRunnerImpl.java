package pl.hycom.jira.plugins.gitlab.integration.scheduler.impl;

import java.util.Random;

import javax.annotation.Nonnull;

import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeException;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomePluginJobRunner;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuff;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuffDao;
import com.atlassian.scheduler.JobRunnerRequest;
import com.atlassian.scheduler.JobRunnerResponse;
import com.atlassian.scheduler.status.JobDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the job scheduling service as well as the {@code JobRunner} implementation.
 * <p>
 * A few notes about this example:
 * </p>
 * <ul>
 * <li>This plugin logs heavily at the {@code INFO} level to help developers follow what is happening and when.
 *      Plugin developers should <strong>not</strong> log this verbosely.  If it isn't something that would help
 *      a system administrator or support engineer track down a problem, it should be logged at {@code DEBUG}
 *      level, if it is logged at all.</li>
 * <li>Note that the job parameters are only used to hold small, {@code Serializable} data that would be
 *      meaningful to every node in the cluster &mdash; in this case, the ID of the active object that
 *      the job is going to be working with.  In particular, note that components like managers, services,
 *      and DAOs do not belong in it.  Inject them into your {@code JobRunner} implementation, instead.
 *      Your local component is not something that other nodes of the cluster can normally share, and it
 *      would take some very ugly serialization hacks to make that work.</li>
 * <li>Similarly, although you may include your plugin's own {@code Serializable} data types in the parameter
 *      map, be aware that this will break if the serialized form changes.  Your plugin can detect this by
 *      looking up its own jobs during initialization and verifying them.  If deserialization is broken,
 *      then its jobs will return {@code false} for {@link JobDetails#isRunnable()} <strong>even after the
 *      {@code JobRunner} is registered</strong>.  This problem can be avoided by using only simple Java
 *      types like {@code Long}, {@code String}, {@code ArrayList}, and {@code HashMap} in the parameter
 *      map.</li>
 * </ul>
 *
 * @since v1.0
 */
public class AwesomePluginJobRunnerImpl implements AwesomePluginJobRunner
{
    private static final Logger LOG = LoggerFactory.getLogger(AwesomePluginJobRunnerImpl.class);

    // These are only used by the example objects and wouldn't be useful to most real plugins
    private static final Random RANDOM = new Random();

    // Injected dependencies
    private final AwesomeStuffDao awesomeStuffDao;




    public AwesomePluginJobRunnerImpl(final AwesomeStuffDao awesomeStuffDao)
    {
        this.awesomeStuffDao = awesomeStuffDao;

        LOG.info("Job runner instance created");
    }



    @Override
    public JobRunnerResponse runJob(JobRunnerRequest request)
    {
        final Integer awesomeId = (Integer)request.getJobConfig().getParameters().get(AWESOME_ID);
        try
        {
            if (awesomeId == null)
            {
                throw new AwesomeException("Tried to run job " + request.getJobId() + " without an awesomeId parameter?!");
            }
            final AwesomeStuff stuff = awesomeStuffDao.findById(awesomeId);
            if (stuff == null)
            {
                return JobRunnerResponse.failed("The awesome stuff with id=" + awesomeId + " no longer exists!");
            }
            return JobRunnerResponse.success(process(stuff, request));
        }
        catch (AwesomeException e)
        {
            return JobRunnerResponse.failed(e);
        }
    }


    private String process(@Nonnull AwesomeStuff stuff, @Nonnull JobRunnerRequest request) throws AwesomeException
    {
        LOG.info("Running job for awesome stuff '" + stuff.getName() + "': " + request);

        // Do really interesting stuff.  All we're going to do is generate a random number
        // and return a short (limited to 255 chars!) message about it.  Although returning
        // a message with JobRunnerRequest.success(String) is optional, it could be helpful
        // to say something about what your scheduled job accomplished, like "Purged 47 old
        // widgets." or "Skipping backup because there have been no configuration changes
        // since the last one." or "Queued 24958 emails for 2 marketing events."  There are
        // no plans for translating this information; like logging, it is just meant to help
        // with troubleshooting.
        final int num = RANDOM.nextInt();
        return "I've decided that I really like the number " + num + ", too.";
    }
}
