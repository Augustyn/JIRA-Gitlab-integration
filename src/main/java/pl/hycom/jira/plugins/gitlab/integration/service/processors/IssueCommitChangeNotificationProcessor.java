package pl.hycom.jira.plugins.gitlab.integration.service.processors;

/*
 * <p>Copyright (c) 2016, Authors
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

import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.mock.issue.MockIssue;
import com.atlassian.jira.user.ApplicationUser;
//import com.atlassian.jira.user.MockApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.service.processors.ProcessorInterface;

import java.util.HashMap;
import java.util.Map;

//import com.atlassian.jira.user.DelegatingApplicationUser;
@Component
public class IssueCommitChangeNotificationProcessor implements ProcessorInterface
{
    @Autowired
    private EventPublisher eventPublisherAccess;

//    private ApplicationUser publishingUser = new MockApplicationUser("GitlabPluginEventPublisherUser");
    //TODO replace with actual user

    @Override
    public void execute(Commit commitInfo)
    {
        Issue currentIssue = new MockIssue(); //TODO retrieve issue from CommitInfo once commitData is implemented
        Map<String,Object> eventParameters = new HashMap<String,Object>();


//        IssueEvent gitCommitEvent = new IssueEvent(currentIssue,eventParameters,publishingUser, EventType.ISSUE_UPDATED_ID);

//        eventPublisherAccess.publish(gitCommitEvent);
    }
}
