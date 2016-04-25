package pl.hycom.jira.plugins.gitlab.integration.gitpanel.service;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl.Commit;

import java.util.List;

/**
 * Created by Kamil Rogowski on 22.04.2016.
 */
@Log4j
@RunWith(MockitoJUnitRunner.class)
public class CommitServiceTest {

    @InjectMocks
    private CommitService commitService;

    @Test
    public void testGetAllCommits() throws Exception {
        List<Commit> commits = commitService.getAllCommits();
        for (Commit commit : commits)
            log.info(commit);
      //  Assert.assertNotNull(commits);
    }
}