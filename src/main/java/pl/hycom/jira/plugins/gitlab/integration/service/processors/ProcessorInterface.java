package pl.hycom.jira.plugins.gitlab.integration.service.processors;

import pl.hycom.jira.plugins.gitlab.integration.model.CommitData;

/**
 * Created by Thorgal on 08.04.2016.
 */
public interface ProcessorInterface {
    public void execute(CommitData commitInfo);
}
