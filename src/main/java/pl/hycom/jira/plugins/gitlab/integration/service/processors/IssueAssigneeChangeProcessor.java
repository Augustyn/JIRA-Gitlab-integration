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
 *
 */
package pl.hycom.jira.plugins.gitlab.integration.service.processors;

import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.UpdateIssueRequest;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.UserUtils;
import com.atlassian.jira.user.util.UserManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigManagerDao;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.service.CommitManager;

import javax.inject.Inject;
import java.util.regex.Pattern;

/**
 *  Class is responsible for changing issue assignee. Assignee is changed when there's one of an patterns:
 *  <pre>
 *      <ul><li>assign=>login</li>
 *          <li>asign=>login</li>
 *          <li>a=>login</li>
 *          <li>ass=>login</li>
 *      </ul>
 *  </pre>
 */
@Log4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Inject)) //Inject all final variables.
public class IssueAssigneeChangeProcessor implements ProcessorInterface {
//FIXME: this processor. It doesn't do logic it was designed for.
//FIXME: Skip the processor when full reindexing/Run processor only when fetching new commits.
    private final IssueManager issueManager;
    private final UserManager userManager;
    private final JiraAuthenticationContext authenticationContext;
    private final ConfigManagerDao configManager;
    private final CommitManager commitManager;

    private Pattern processorPattern = Pattern.compile("(assign|asing|a|ass)=>(az-AZ)");

    public void execute(Commit commit) {
        String issueKey = commit.getIssueKey();
        if (StringUtils.isBlank(issueKey)) {
            log.info("skipping processor: " + this.getClass().getName() +" due commit doesn't contain any project key");
            return;
        }
        try {
            //TODO: save 'git' user in configuration. Use this user if there's user.
            ApplicationUser executor = UserUtils.getUserByEmail(commit.getAuthorEmail());
            authenticationContext.setLoggedInUser(executor);

            ApplicationUser newAssignee = UserUtils.getUserByEmail(commit.getAuthorEmail());
            if (newAssignee == null) {
                log.info("User with email " + commit.getAuthorEmail() + " not found. Skipping assignee change");
                return;
            }

            MutableIssue issue = issueManager.getIssueObject(issueKey);
            issue.setAssignee(newAssignee);
            issueManager.updateIssue(executor, issue, UpdateIssueRequest.builder().build());

        } catch (Exception e) {
            log.info("Failed to update issue, with message: " + e.getMessage() +". Enable debug for more info.");
            log.debug("Detailed stack-trace: ", e);
        }
    }

}
