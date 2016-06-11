package ut.pl.hycom.jira.plugins.gitlab.integration.service;
/*
 * <p>Copyright (c) 2016, Damian Deska, Kamil Rogowski
 * Project:  gitlab-integration.</p>
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

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.hycom.jira.plugins.gitlab.integration.dao.CommitRepository;
import pl.hycom.jira.plugins.gitlab.integration.dao.ICommitDao;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.service.processors.IssueWorklogChangeProcessor;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Kamil Rogowski on 22.04.2016.
 */
@Log4j
@RunWith(MockitoJUnitRunner.class)
public class CommitServiceTest {

    @InjectMocks
    private CommitRepository commitService;
    private String urlMock = "https://gitlab.com/api/v3/projects/1063546/repository/commits";
    private String privateTokenMock = "KCi3MfkU7qNGJCe3pQUW";
    @InjectMocks
    private IssueWorklogChangeProcessor worklogChangeProcessor;
    @Mock
    private ICommitDao dao;

    @Test
    public void testGetNewCommits() throws Exception {
        int pageSize = 3;
        List<Commit> commits = commitService.getNewCommits(urlMock, privateTokenMock, pageSize, 3);
        assertThat("Commits size should be as ", commits.size(), is(equalTo(pageSize)));
    }

    @Test
    public void testGetOneCommit() throws Exception {
        String id = "404dd04e1d6279f76db51f64c80edf6c2bd96bf2";
        Commit commit = commitService.getOneCommit(urlMock, privateTokenMock, "master");
        assertThat("Commit id's should be the same: ", commit.getId(), is(equalTo(id)));
    }
    @Test
    public void testGetPattern() throws Exception{
        Commit commit = commitService.getOneCommit(urlMock, privateTokenMock, "master");
        commit.setMessage("1y 10w 12d 1h 0m 32s");
        StringBuilder expectedResult = new StringBuilder();
        expectedResult.append("1y10w12d1h0m32s");
        worklogChangeProcessor.execute(commit);
        assertThat("expectedResult and extracted substring should be equal",worklogChangeProcessor.getExtractedMessage()
                ,is(equalTo(expectedResult)));
    }
}