package pl.hycom.jira.plugins.gitlab.integration.controller.service;

import pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl.Commit;

import java.util.List;

/**
 * Created by Kamil Rogowski on 22.04.2016.
 */
public interface ICommitService {

    List<Commit> getNewCommits(int perPage, int pageNumber);
    Commit getOneCommit(String shaSum);
}
