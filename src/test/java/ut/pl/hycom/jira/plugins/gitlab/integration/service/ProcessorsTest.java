package ut.pl.hycom.jira.plugins.gitlab.integration.service;

import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import pl.hycom.jira.plugins.gitlab.integration.dao.CommitRepository;
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

    @Test
    public void testGetMessageWithEnum() throws Exception{
        Commit commit = commitService.getOneCommit(urlMock, privateTokenMock, "master");
        commit.setMessage("1y 10w 12d 1h 0m 32s");
        String expectedResult = "1y10w12d1h0m32s";
        String extractedResult = "";
        List<IssueWorklogChangeProcessor.Time> timesList = worklogChangeProcessor.getExtractedMsg(commit);
        for(int i =0;i < timesList.size();i++){
            extractedResult+=timesList.get(i).getFieldValue() + timesList.get(i).getFieldName();
        }
        Assert.assertEquals(expectedResult ,extractedResult );
    }
    @Test
    public void convertTimeToSecondsTest(){
        Commit commit = commitService.getOneCommit(urlMock, privateTokenMock, "master");
        commit.setMessage("1y 10w 12d 1h 0m 32s");
        int expectedValue = 38645358;
        Assert.assertEquals(expectedValue,
                worklogChangeProcessor.getTimeConvertedToSeconds(worklogChangeProcessor.getExtractedMsg(commit)));
    }

}
