package pl.hycom.jira.plugins.gitlab.integration.scheduler;

/**
 * Thrown whenever things are less awesome than they should be.
 *
 * @since v1.0
 */
public class AwesomeException extends Exception
{
    public AwesomeException(String message)
    {
        super(message);
    }

    public AwesomeException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public AwesomeException(Throwable cause)
    {
        super(cause);
    }
}
