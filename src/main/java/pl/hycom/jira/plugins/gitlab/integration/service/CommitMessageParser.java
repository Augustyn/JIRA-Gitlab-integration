package pl.hycom.jira.plugins.gitlab.integration.service;

import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

/**
 * Created by vagrant on 6/18/16.
 */
public interface CommitMessageParser
{
    public String findIssue(Commit commit, int projectID);
}
