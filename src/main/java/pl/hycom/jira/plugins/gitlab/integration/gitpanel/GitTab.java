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

import com.atlassian.jira.plugin.issuetabpanel.AbstractIssueTabPanel;
import com.atlassian.jira.plugin.issuetabpanel.IssueTabPanel;
import com.atlassian.jira.issue.tabpanels.GenericMessageAction;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.user.ApplicationUser;
import lombok.extern.log4j.Log4j;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.service.CommitService;

import java.util.Collections;
import java.util.List;

@Log4j
public class GitTab extends AbstractIssueTabPanel implements IssueTabPanel
{

    public List getActions(Issue issue, ApplicationUser remoteUser) {
        CommitService commitService = new CommitService();
        List<Commit> commitsListForIssue = commitService.getAllIssueCommits(issue);
        Commit commit = commitsListForIssue.get(0);
        log.warn("Commit: " + commit);
        return Collections.singletonList(new GenericMessageAction("Tu będzie znajdować się zawartość zakładki GitTab"));
    }


    public boolean showPanel(Issue issue, ApplicationUser remoteUser)
    {
        return true;
    }
}
