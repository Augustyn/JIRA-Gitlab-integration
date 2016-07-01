package ut.pl.hycom.jira.plugins.gitlab.integration.service;

import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.hycom.jira.plugins.gitlab.integration.dao.CommitRepository;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigEntity;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.service.processors.IssueWorklogChangeProcessor;

import java.util.List;

/**
 * Created by Thorgal on 14.06.2016.
 */
@Log4j
@RunWith(MockitoJUnitRunner.class)
public class ProcessorsTest {
    @InjectMocks
    private CommitRepository commitService;
    private String urlMock = "https://gitlab.com/api/v3/projects/1063546/repository/commits";
    private String privateTokenMock = "KCi3MfkU7qNGJCe3pQUW";
    @InjectMocks
    private IssueWorklogChangeProcessor worklogChangeProcessor;
    @Mock private ConfigEntity config;


    @Test
    public void testGetMessageWithEnum() throws Exception {
        Mockito.when(config.getLink()).thenReturn("https://gitlab.com/");
        Mockito.when(config.getGitlabProjectId()).thenReturn(1063546);
        Mockito.when(config.getSecret()).thenReturn(privateTokenMock);
        Commit commit = commitService.getOneCommit(config, "master");
        commit.setMessage("1y 10w 12d 1h 0m 32s");
        String expectedResult = "1y10w12d1h0m32s";
        String extractedResult = "";
        for (IssueWorklogChangeProcessor.Time aTimesList : worklogChangeProcessor.getExtractedMsg(commit)) {
            extractedResult += aTimesList.getFieldValue() + aTimesList.getFieldName();
        }
        Assert.assertEquals(expectedResult ,extractedResult );
    }
    @Test
    public void convertTimeToSecondsTest() {
        Mockito.when(config.getLink()).thenReturn("https://gitlab.com/");
        Mockito.when(config.getGitlabProjectId()).thenReturn(1063546);
        Mockito.when(config.getSecret()).thenReturn(privateTokenMock);
        Commit commit = commitService.getOneCommit(config, "master");
        commit.setMessage("1y 10w 12d 1h 0m 32s");
        int expectedValue = 38645358;
        Assert.assertEquals(expectedValue,
                worklogChangeProcessor.getTimeConvertedToSeconds(worklogChangeProcessor.getExtractedMsg(commit)));
    }

}
