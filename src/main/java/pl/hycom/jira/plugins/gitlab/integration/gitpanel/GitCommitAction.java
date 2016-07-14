package pl.hycom.jira.plugins.gitlab.integration.gitpanel;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.plugin.issuetabpanel.IssueAction;
import com.atlassian.jira.plugin.profile.UserFormatManager;
import com.atlassian.jira.template.VelocityTemplatingEngine;
import com.atlassian.jira.util.JiraVelocityHelper;
import com.atlassian.jira.util.velocity.DefaultVelocityRequestContextFactory;
import com.google.common.collect.Maps;
import com.opensymphony.util.TextUtils;
import lombok.extern.log4j.Log4j;
import org.apache.velocity.exception.VelocityException;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import java.util.Date;
import java.util.Map;

import static com.atlassian.jira.template.TemplateSources.file;

/**
 * Basic "action" that is displayed in gitTabPanel for given commit.
 *
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
@Log4j
public class GitCommitAction implements IssueAction {
    //TODO: implement me

    private final Commit commit;
    private static final String PLUGIN_TEMPLATE = "templates/tabpanels/git-tab.vm";

    public GitCommitAction(Commit commit) {
        this.commit = commit;
    }

    @Override
    public Date getTimePerformed() {
        return commit.getCreatedAt();
    }
    @Override
    public String getHtml() {
        final Map<String, Object> params = Maps.newHashMap();
        populateVelocityParams(params);
        final VelocityTemplatingEngine templateEngine = ComponentAccessor.getComponent(VelocityTemplatingEngine.class);
        try {
            /*ImmutableMap.<String, Object>of("commit", commit);*/
            return templateEngine.render(file(PLUGIN_TEMPLATE)).applying(params).asHtml();
        } catch (VelocityException e) {
            log.error("Error while rendering velocity template for '" + PLUGIN_TEMPLATE + "' with commit: " + commit, e);
            return "Velocity template generation failed.";
        }
    }

    @Override
    public boolean isDisplayActionAllTab() {
        return Boolean.FALSE;
    }

    /**
     * This will populate the passed in map with this object referenced as "action" and the rendered comment body as
     * "renderedContent".
     *
     * @param params map of params to populate
     */
    private void populateVelocityParams(Map<String, Object> params) {
        params.put("action", this);
        params.put("velocityhelper", new JiraVelocityHelper(ComponentAccessor.getFieldManager()));
        params.put("requestContext", new DefaultVelocityRequestContextFactory(ComponentAccessor.getApplicationProperties()).getJiraVelocityRequestContext());
        params.put("userformat", ComponentAccessor.getComponent(UserFormatManager.class));
        params.put("textutils", new TextUtils());
        params.put("commit", commit);
    }
}
