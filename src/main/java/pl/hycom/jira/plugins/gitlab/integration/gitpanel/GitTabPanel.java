package pl.hycom.jira.plugins.gitlab.integration.gitpanel;
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

import com.atlassian.jira.bc.issue.comment.property.CommentPropertyService;
import com.atlassian.jira.datetime.DateTimeFormatter;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.RendererManager;
import com.atlassian.jira.issue.comments.CommentManager;
import com.atlassian.jira.issue.comments.CommentPermissionManager;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutManager;
import com.atlassian.jira.issue.fields.renderer.comment.CommentFieldRenderer;
import com.atlassian.jira.issue.tabpanels.CommentTabPanel;
import com.atlassian.jira.issue.tabpanels.GenericMessageAction;
import com.atlassian.jira.permission.ProjectPermissions;
import com.atlassian.jira.plugin.issuetabpanel.GetActionsRequest;
import com.atlassian.jira.plugin.issuetabpanel.IssueAction;
import com.atlassian.jira.plugin.issuetabpanel.IssueTabPanelModuleDescriptor;
import com.atlassian.jira.plugin.issuetabpanel.ShowPanelRequest;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.template.soy.SoyTemplateRendererProvider;
import com.atlassian.jira.user.ApplicationUser;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.service.CommitService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j
@Controller
public class GitTabPanel extends CommentTabPanel {

    private IssueTabPanelModuleDescriptor descriptor;

    @Autowired private CommitService commitService;
    @Autowired private PermissionManager permissionManager;

    public GitTabPanel(CommentManager commentManager, CommentPermissionManager commentPermissionManager, IssueManager issueManager, FieldLayoutManager fieldLayoutManager, RendererManager rendererManager, DateTimeFormatter dateTimeFormatter, SoyTemplateRendererProvider soyTemplateRendererProvider, CommentFieldRenderer commentFieldRenderer, CommentPropertyService commentPropertyService, JiraAuthenticationContext jiraAuthenticationContext) {
        super(commentManager, commentPermissionManager, issueManager, fieldLayoutManager, rendererManager, dateTimeFormatter, soyTemplateRendererProvider, commentFieldRenderer, commentPropertyService, jiraAuthenticationContext);
    }

    @Override
    public boolean showPanel(ShowPanelRequest showPanelRequest) {
        final Project project = showPanelRequest.issue().getProjectObject();
        final ApplicationUser user = showPanelRequest.remoteUser();
        final boolean hasPermission = permissionManager.hasPermission(ProjectPermissions.VIEW_DEV_TOOLS, project, user);
        log.info("User: " + (user != null ? user.getUsername() : null) + " requested to see git panel for project: "
                + (project != null ? project.getKey() : null) + ", issue: " + showPanelRequest.issue().getKey()
                + ". Displaying panel? " + hasPermission);
        return hasPermission;
    }


    @Override
    public List<IssueAction> getActions(GetActionsRequest getActionsRequest) {
        List<Commit> commitsListForIssue = null;
        try {
            commitsListForIssue = commitService.getAllIssueCommits(getActionsRequest.issue());
        } catch (IOException e) {
            log.info("There was an error while trying to get commits for issue: " + getActionsRequest.issue(), e);
        }
        Commit commit = commitsListForIssue!= null && commitsListForIssue.isEmpty() ? commitsListForIssue.get(0) : null;
        log.warn("Commit: " + commit);
        return Collections.singletonList(new GenericMessageAction("Tu będzie znajdować się zawartość zakładki GitTabPanel"));
    }
}
