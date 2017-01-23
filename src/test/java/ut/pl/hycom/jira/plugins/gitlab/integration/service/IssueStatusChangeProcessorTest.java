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

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.MockApplicationUser;
import com.atlassian.jira.user.util.UserManager;
import lombok.extern.log4j.Log4j;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.hycom.jira.plugins.gitlab.integration.service.processors.IssueStatusChangeProcessor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Log4j
public class IssueStatusChangeProcessorTest {
    @Mock private UserManager userManager;
    @Mock private IssueService issueService;
    @Mock private JiraAuthenticationContext context;
    @Mock private IssueInputParameters params;
    private MockApplicationUser mockUser;
    @Test
    @Ignore //FIXME
    public void getPossibleStatuesTest() throws URISyntaxException, IOException {
        Mockito.when(userManager.getUserByKey("admin")).thenReturn(mockUser);
        /*Mockito.when(issueService.validateTransition(mockUser, 10000L, 31, params));*/
        IssueStatusChangeProcessor statusChanger = new IssueStatusChangeProcessor(/*userManager, context, issueService*/);
        List<String> result = statusChanger.getPossibleIssueStatuses();
        for(String statuses : result) {
            log.info(statuses);
        }
    }

    @Test
    @Ignore //FIXME
    public void changeIssueStatusTest(){
        IssueStatusChangeProcessor statusChanger = new IssueStatusChangeProcessor(/*userManager, context, issueService*/);
        statusChanger.changeIssueStatus();
    }
}
