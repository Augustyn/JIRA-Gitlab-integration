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
package pl.hycom.jira.plugins.gitlab.integration.gitpanel;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.plugin.issuetabpanel.IssueAction;
import com.atlassian.jira.plugin.profile.UserFormatManager;
import com.atlassian.jira.template.VelocityTemplatingEngine;
import com.atlassian.jira.util.JiraVelocityHelper;
import com.atlassian.jira.util.velocity.DefaultVelocityRequestContextFactory;
import com.atlassian.velocity.VelocityHelper;
import com.google.common.collect.Maps;
import com.opensymphony.util.TextUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.velocity.exception.VelocityException;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static com.atlassian.jira.template.TemplateSources.file;

/**
 * Basic "action" that is displayed in gitTabPanel for given commit.
 */
@Log4j
@RequiredArgsConstructor
public class GitCommitAction implements IssueAction {
    private static final String PLUGIN_TEMPLATE = "templates/tabpanels/gitTab-single-commit.vm";
    private final Commit commit;
    private final Map<String,Object> params;
    private final VelocityTemplatingEngine templateEngine;
    @Override
    public Date getTimePerformed() {
        return Date.from(Instant.from(commit.getCreatedAt()));
    }

    @Override
    public String getHtml() {
        try {
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

}
