package ut.pl.hycom.jira.plugins.gitlab.integration.search;

/*
 * <p>Copyright (c) 2016, Damian Deska
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

import org.junit.Test;
import org.mockito.InjectMocks;
import pl.hycom.jira.plugins.gitlab.integration.service.processors.IssueAssigneeChangeProcessor;

/**
 * Created by Damian Deska on 6/14/16.
 */
public class IssueAssigneeChangeProcessorTest {

    @InjectMocks
    private IssueAssigneeChangeProcessor issueAssigneeChange;
    private String jiraUrlMock = "http://vagrant:2990";
    private String issueKeyMock = "PIP-1";

    @Test
    public void checkIssueAssigneeTest() {
        issueAssigneeChange.changeIssueAssignee(jiraUrlMock, issueKeyMock);
    }

}
