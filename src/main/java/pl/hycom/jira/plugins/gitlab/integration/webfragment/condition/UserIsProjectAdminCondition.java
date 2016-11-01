package pl.hycom.jira.plugins.gitlab.integration.webfragment.condition;

import com.atlassian.jira.permission.ProjectPermission;
import com.atlassian.jira.permission.ProjectPermissions;
import com.atlassian.jira.plugin.webfragment.conditions.AbstractWebCondition;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.user.ApplicationUser;

import javax.annotation.Nullable;

import static com.atlassian.jira.plugin.webfragment.conditions.AbstractPermissionCondition.getHasProjectsKey;
import static com.atlassian.jira.plugin.webfragment.conditions.RequestCachingConditionHelper.cacheConditionResultInRequest;

/**
 * @author Augustyn Wilk on 2016-10-30.
 */
public class UserIsProjectAdminCondition extends AbstractWebCondition {
    private final PermissionManager permissionManager;

    public UserIsProjectAdminCondition(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }

    public boolean shouldDisplay(@Nullable final ApplicationUser user, @Nullable JiraHelper jiraHelper) {
        return cacheConditionResultInRequest(getHasProjectsKey(Permissions.PROJECT_ADMIN, user), () ->
            permissionManager.hasProjects(Permissions.PROJECT_ADMIN, user)
        );
    }
}
