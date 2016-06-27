package pl.hycom.jira.plugins.gitlab.integration.scheduler.impl;

import java.util.List;

import com.atlassian.activeobjects.external.ActiveObjects;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeException;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuff;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuffDao;

/**
 * Simple memory-based DAO for awesome stuff.
 *
 * @since v1.0
 */
public class AwesomeStuffDaoImpl implements AwesomeStuffDao
{
    private final ActiveObjects ao;

    public AwesomeStuffDaoImpl(final ActiveObjects ao)
    {
        this.ao = ao;
    }

    @Override
    public AwesomeStuff findById(int id) throws AwesomeException
    {
        return ao.get(AwesomeStuff.class, id);
    }

    @Override
    public AwesomeStuff[] findByAll() throws AwesomeException
    {
        return ao.find(AwesomeStuff.class);
    }

    @Override
    public AwesomeStuff create(String name) throws AwesomeException
    {
        if (name == null || name.isEmpty())
        {
            throw new AwesomeException("Please specify a name for your awesome stuff");
        }
        final AwesomeStuff stuff = ao.create(AwesomeStuff.class);
        stuff.setName(name);
        stuff.save();
        return stuff;
    }

    @Override
    public void remove(AwesomeStuff stuff) throws AwesomeException
    {
        ao.delete(stuff);
    }
}
