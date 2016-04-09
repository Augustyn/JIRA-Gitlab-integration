package pl.hycom.jira.plugins.gitlab.integration.nowaZakladka.api;

import pl.hycom.jira.plugins.gitlab.integration.nowaZakladka.impl.CommitData;

/**
 * Created by Thorgal on 08.04.2016.
 */
public interface ProcessorInterface {
    public void execute(CommitData commitInfo);
}
