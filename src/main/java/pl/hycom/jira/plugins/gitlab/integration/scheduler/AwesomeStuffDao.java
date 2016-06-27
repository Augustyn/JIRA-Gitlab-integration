package pl.hycom.jira.plugins.gitlab.integration.scheduler;

/**
 * Something that accesses database objects
 *
 * @since v1.0
 */
public interface AwesomeStuffDao
{
    AwesomeStuff findById(int id) throws AwesomeException;
    AwesomeStuff[] findByAll() throws AwesomeException;
    AwesomeStuff create(String name) throws AwesomeException;
    void remove(AwesomeStuff stuff) throws AwesomeException;
}
