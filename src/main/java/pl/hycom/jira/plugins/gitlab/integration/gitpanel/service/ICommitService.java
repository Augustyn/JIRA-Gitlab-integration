package pl.hycom.jira.plugins.gitlab.integration.gitpanel.service;

import pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl.Commit;

import java.util.List;

/**
 * Created by Kamil Rogowski on 22.04.2016.
 */
public interface ICommitService {

    List<Commit> getNewCommits(int perPage);
    Commit getOneCommit(String shaSum);
}
