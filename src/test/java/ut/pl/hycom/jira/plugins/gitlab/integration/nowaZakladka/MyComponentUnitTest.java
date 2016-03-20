package ut.pl.hycom.jira.plugins.gitlab.integration.nowaZakladka;

import org.junit.Test;
import pl.hycom.jira.plugins.gitlab.integration.nowaZakladka.api.MyPluginComponent;
import pl.hycom.jira.plugins.gitlab.integration.nowaZakladka.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}