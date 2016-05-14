package pl.hycom.jira.plugins.gitlab.integration.gitpanel.service;

import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.dao.CommitRepository;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.dao.ICommitDao;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl.Commit;

import java.util.List;

/**
 * Created by Kamil Rogowski on 22.04.2016.
 */
@Log4j
@RunWith(MockitoJUnitRunner.class)
public class CommitServiceTest {

    @InjectMocks
    private CommitRepository commitService;
    @Mock
    private ICommitDao dao;

    @Test
    public void testGetNewCommits() throws Exception {
        int pageSize = 3;
        List<Commit> commits = commitService.getNewCommits(pageSize, 3);
        Assert.assertTrue(commits.size() == pageSize);
    }

    @Test
    public void testGetOneCommit() throws Exception {
        String id = "404dd04e1d6279f76db51f64c80edf6c2bd96bf2";
        Commit commit = commitService.getOneCommit("master");
        Assert.assertTrue(commit.getId().equals(id));
    }
}