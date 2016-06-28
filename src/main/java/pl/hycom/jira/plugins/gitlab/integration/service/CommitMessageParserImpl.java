package pl.hycom.jira.plugins.gitlab.integration.service;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.project.Project;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vagrant on 6/18/16.
 */


@Component
public class CommitMessageParserImpl implements CommitMessageParser {
    @Override
    public String findIssue(Commit commit, int projectID)
    {
        Project currentProject = ComponentAccessor.getProjectManager().getProjectObj( (long) projectID);
        String currentProjectKey = currentProject.getKey();
        String commitMessage = commit.getMessage();

        Pattern pattern = Pattern.compile("\\[" + currentProjectKey + "-(\\d+)");
        Matcher matcher = pattern.matcher(commitMessage);

        String issueIdString = matcher.group(1);

        return issueIdString;
    }
}
