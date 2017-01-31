/*
 * <p>Copyright (c) 2016, Authors</p>
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p>
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
package ut.pl.hycom.jira.plugins.gitlab.integration.service;

import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.hycom.jira.plugins.gitlab.integration.dao.CommitRepository;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigEntity;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.service.processors.IssueWorklogChangeProcessor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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

    private Commit commit;

    @Before
    public void setUp() {
        commit = new Commit().withAuthorEmail("Test Author").withAuthorEmail("test@example.com").withId("8b65f529727152439980bf15df6018547c6006b6");
    }

    @Test
    public void testGetMessageWithEnum() throws Exception {
        //when
        commit.setMessage("1y 10w 12d 1h 0m 32s");
        ///then
        String expectedResult = "1y10w12d1h0m32s";
        String extractedResult = "";
        for (IssueWorklogChangeProcessor.Time aTimesList : worklogChangeProcessor.getExtractedMsg(commit)) {
            extractedResult += aTimesList.getFieldValue() + aTimesList.getFieldName();
        }
        Assert.assertEquals(expectedResult ,extractedResult );
    }
    @Test
    public void convertTimeToSecondsTest() {
        //when
        commit.setMessage("1y 10w 12d 1h 0m 32s");
        int expectedValue = 38624432;
        //then
        Assert.assertEquals(expectedValue, worklogChangeProcessor.getTimeConvertedToSeconds(worklogChangeProcessor.getExtractedMsg(commit)));
        commit.setMessage("1h");
        assertThat("An hour should be 3600 seconds", worklogChangeProcessor.getTimeConvertedToSeconds(worklogChangeProcessor.getExtractedMsg(commit)), is(3600));
        commit.setMessage("1d");
        assertThat("An day should be 86400 seconds", worklogChangeProcessor.getTimeConvertedToSeconds(worklogChangeProcessor.getExtractedMsg(commit)), is(86400));
    }

}
