package pl.hycom.jira.plugins.gitlab.integration.service.processors;

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

import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.UpdateIssueRequest;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.UserUtils;
import com.atlassian.jira.user.util.UserManager;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.controller.JiraSectionAction;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigEntity;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigManagerDao;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.service.CommitMessageParser;


/**
 * Created by Damian Deska on 6/14/16.
 */
@Log4j
@Component
public class IssueAssigneeChangeProcessor implements ProcessorInterface {

    @Autowired
    IssueManager issueManager;
    @Autowired
    UserManager userManager;
    @Autowired
    JiraAuthenticationContext authenticationContext;
    @Autowired
    ConfigManagerDao configManagerDao;
    @Autowired
    CommitMessageParser commitMessageParser;
    //FIXME: gettin clientid and project id
    JiraSectionAction jiraSectionAction;


    public void execute(Commit commit){

        ApplicationUser executor = userManager.getUserByKey(jiraSectionAction.getClientId());
        authenticationContext.setLoggedInUser(executor);
        ApplicationUser newAssignee = UserUtils.getUserByEmail(commit.getAuthorEmail());

        if(newAssignee == null) {
            log.info("User with email " + commit.getAuthorEmail() + "not found. Skipping assignee change");
            return;
        }

        String issueKey = commit.getIssueKey() != null ? commit.getIssueKey() : commitMessageParser.findIssue(commit, Integer.getInteger(jiraSectionAction.getProjectId()));

        try {
            MutableIssue issue = issueManager.getIssueObject(issueKey);
            issue.setAssignee(newAssignee);

            issueManager.updateIssue(executor, issue, UpdateIssueRequest.builder().build());

        } catch (Exception e) {
            log.info("Failed to update issue. Enable debug for more info. Exception message:\n" + e.getMessage());
        }
    }

}
