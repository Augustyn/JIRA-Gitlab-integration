package pl.hycom.jira.plugins.gitlab.integration.gitpanel;

import com.atlassian.core.util.map.EasyMap;
import com.atlassian.jira.config.properties.APKeys;
import com.atlassian.jira.config.properties.ApplicationProperties;
import com.atlassian.jira.web.SessionKeys;
import com.opensymphony.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.plugin.issuetabpanel.AbstractIssueTabPanel;
import com.atlassian.jira.plugin.issuetabpanel.IssueTabPanel;
import com.atlassian.jira.issue.tabpanels.GenericMessageAction;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.plugin.webfragment.contextproviders.AbstractJiraContextProvider;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public abstract class JiraGitConfigurationController extends AbstractJiraContextProvider
{
    @Autowired
    private ApplicationProperties applicationProperties;


    public JiraGitConfigurationController()
    {

    }

    public void setApplicationProperties (ApplicationProperties applicationProperties)
    {
        this.applicationProperties = applicationProperties;
    }

    /*public Map getContextMap(User user, JiraHelper jiraHelper)
    {
        int historyIssues = 0;
        if (jiraHelper != null && jiraHelper.getRequest() != null)
        {
            UserHistory history = (UserHistory) jiraHelper.getRequest().getSession().getAttribute(SessionKeys.USER_ISSUE_HISTORY);
            if (history != null)
            {
                historyIssues = history.getIssues().size();
            }
        }
        int logoHeight = TextUtils.parseInt(applicationProperties.getDefaultBackedString(APKeys.JIRA_LF_LOGO_HEIGHT));
        String historyHeight = String.valueOf(80 + logoHeight + (25 * historyIssues));
        String filterHeight = String.valueOf(205 + logoHeight);
        return EasyMap.build("historyWindowHeight", historyHeight,
                "filtersWindowHeight", filterHeight);
    }*/
}