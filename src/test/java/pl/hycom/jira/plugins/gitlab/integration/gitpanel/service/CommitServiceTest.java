package pl.hycom.jira.plugins.gitlab.integration.gitpanel.service;

import lombok.extern.log4j.Log4j;
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
        List<Commit> commits = commitService.getNewCommits(3, 1);
        for (Commit commit : commits)
            log.info(commit);
      //  Assert.assertNotNull(commits);
    }

    @Test
    public void testGetOneCommit() throws Exception {
        Commit commit = commitService.getOneCommit("master");
        log.info(commit);
    }
}