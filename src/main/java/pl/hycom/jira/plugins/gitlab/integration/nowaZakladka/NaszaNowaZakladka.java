package pl.hycom.jira.plugins.gitlab.integration.nowaZakladka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.plugin.issuetabpanel.AbstractIssueTabPanel;
import com.atlassian.jira.plugin.issuetabpanel.IssueTabPanel;
import com.atlassian.jira.issue.tabpanels.GenericMessageAction;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.user.ApplicationUser;
import java.util.Collections;
import java.util.List;

public class NaszaNowaZakladka extends AbstractIssueTabPanel implements IssueTabPanel
{
    private static final Logger log = LoggerFactory.getLogger(NaszaNowaZakladka.class);

    public List getActions(Issue issue, ApplicationUser remoteUser) {
        return Collections.singletonList(new GenericMessageAction("Say Hy To The World"));
    }

    public boolean showPanel(Issue issue, ApplicationUser remoteUser)
    {
        return true;
    }
}
