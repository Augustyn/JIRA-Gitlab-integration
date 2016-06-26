package pl.hycom.jira.plugins.gitlab.integration.scheduler;

import net.java.ao.Entity;
import net.java.ao.Preload;

/**
 * Some kind of object that we want to do things with.
 *
 * @since v1.0
 */
@Preload
public interface AwesomeStuff extends Entity
{
    String getName();
    void setName(String name);
}
