package pl.hycom.jira.plugins.gitlab.integration.gitpanel.dao;

import pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl.Commit;

import java.util.List;

/**
 * Created by Kamil Rogowski on 22.04.2016.
 */
public interface ICommitDao {

    void indexNewCommits(List<Commit> commitList);
    List<Commit> getNewCommits(int perPage, int pageNumber);
    Commit getOneCommit(String shaSum);
}
