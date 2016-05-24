package pl.hycom.jira.plugins.gitlab.integration.gitpanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.plugin.issuetabpanel.AbstractIssueTabPanel;
import com.atlassian.jira.plugin.issuetabpanel.IssueTabPanel;
import com.atlassian.jira.issue.tabpanels.GenericMessageAction;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.user.ApplicationUser;
import java.util.Collections;
import java.util.List;

public class GitTab extends AbstractIssueTabPanel implements IssueTabPanel
{
    private static final Logger log = LoggerFactory.getLogger(GitTab.class);

    public List getActions(Issue issue, ApplicationUser remoteUser) {
        return Collections.singletonList(new GenericMessageAction("Tu będzie znajdować się zawartość zakładki GitTab"));
    }

    public boolean showPanel(Issue issue, ApplicationUser remoteUser)
    {
        return true;
    }
}
