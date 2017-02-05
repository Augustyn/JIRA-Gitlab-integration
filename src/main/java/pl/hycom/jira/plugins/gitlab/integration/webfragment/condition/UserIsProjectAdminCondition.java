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
 */
package pl.hycom.jira.plugins.gitlab.integration.webfragment.condition;

import com.atlassian.jira.plugin.webfragment.conditions.AbstractWebCondition;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.user.ApplicationUser;

import javax.annotation.Nullable;

import static com.atlassian.jira.plugin.webfragment.conditions.AbstractPermissionCondition.getHasProjectsKey;
import static com.atlassian.jira.plugin.webfragment.conditions.RequestCachingConditionHelper.cacheConditionResultInRequest;

/**
 * Based on jira UserIsProjectAdminCondition
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
