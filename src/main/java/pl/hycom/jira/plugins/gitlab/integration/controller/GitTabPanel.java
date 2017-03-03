/*
 *  Copyright 2016-2017 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package pl.hycom.jira.plugins.gitlab.integration.controller;

import com.atlassian.jira.issue.tabpanels.GenericMessageAction;
import com.atlassian.jira.permission.ProjectPermissions;
import com.atlassian.jira.plugin.issuetabpanel.*;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.user.ApplicationUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.ActionFactory;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.service.impl.CommitService;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject)) //Inject all final variables.
public class GitTabPanel extends AbstractIssueTabPanel2 {

    private IssueTabPanelModuleDescriptor descriptor;

    private final CommitService commitService;
    private final PermissionManager permissionManager;
    private final ActionFactory actionFactory;

    @Override
    public ShowPanelReply showPanel(ShowPanelRequest showPanelRequest) {
        try {
            final Project project = showPanelRequest.issue().getProjectObject();
            final ApplicationUser user = showPanelRequest.remoteUser();
            final boolean hasPermission = permissionManager.hasPermission(ProjectPermissions.WORK_ON_ISSUES, project, user);
            log.debug("User: " + (user != null ? user.getUsername() : null) + " requested to see git panel for project: "
                    + project.getKey() + ", issue: " + showPanelRequest.issue().getKey()
                    + ". Displaying panel? " + hasPermission);
            return ShowPanelReply.create(hasPermission);
        } catch (Exception e) {
            log.error("Exception occurred while trying to determine if panel should be seen, with message: " + e.getMessage(), e);
            return ShowPanelReply.create(Boolean.FALSE);
        }
    }


    @Override
    public GetActionsReply getActions(GetActionsRequest getActionsRequest) {
        try {
            List<Commit> commitsListForIssue = commitService.getAllIssueCommits(getActionsRequest.issue());
            log.info("Returning actions for commits: " + commitsListForIssue);
            final List<IssueAction> actions = createActionList(commitsListForIssue);
            if (actions.isEmpty()) {
                actions.add(new GenericMessageAction("There are no commits for this issue, yet. Maybe you should add some?"));
            }
            return GetActionsReply.create(actions);
        } catch (IOException e) {
            log.info("There was an error while trying to get commits for issue: " + getActionsRequest.issue(), e);
        }
        return GetActionsReply.create(Collections.singletonList(new GenericMessageAction("There was an error processing commits for this issue. Please consult your administrator")));
    }

    private List<IssueAction> createActionList(List<Commit> commitsListForIssue) {
        return commitsListForIssue.stream().map(actionFactory::createAction).collect(Collectors.toList());
    }
}
