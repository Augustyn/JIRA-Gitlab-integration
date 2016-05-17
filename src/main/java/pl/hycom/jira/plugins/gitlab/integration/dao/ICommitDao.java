package pl.hycom.jira.plugins.gitlab.integration.dao;

import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import java.util.List;

/**
 * Created by Kamil Rogowski on 22.04.2016.
 */
public interface ICommitDao {

    List<Commit> getNewCommits(int perPage, int pageNumber);
    Commit getOneCommit(String shaSum);
}
