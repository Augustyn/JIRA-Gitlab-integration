/*
 *  Copyright 2016-2017 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package pl.hycom.jira.plugins.gitlab.integration.gitpanel;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.properties.ApplicationProperties;
import com.atlassian.jira.issue.fields.FieldManager;
import com.atlassian.jira.plugin.profile.UserFormatManager;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.template.VelocityTemplatingEngine;
import com.atlassian.jira.util.JiraVelocityHelper;
import com.atlassian.jira.util.JiraVelocityUtils;
import com.atlassian.jira.util.velocity.DefaultVelocityRequestContextFactory;
import com.opensymphony.util.TextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import javax.inject.Inject;
import java.util.Map;

/**
 * Factory for
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject)) //Inject all final variables.
public class ActionFactory {
    private final JiraAuthenticationContext authenticationContext;
    private final VelocityTemplatingEngine velocityEngine;
/*    private final UserFormatManager component;*/
    private final FieldManager fieldManager;
    private final ApplicationProperties applicationProperties;

    public GitCommitAction createAction(Commit commit) {
        final Map<String, Object> velocityParams = this.populateVelocityParams();
        velocityParams.put("commit", commit);
        return new GitCommitAction(commit, velocityParams, velocityEngine);
    }

    /**
     * This will populate the map with this object referenced as "action" and the rendered comment body as
     * "renderedContent".
     */
    private Map<String, Object> populateVelocityParams() {
        final Map<String, Object> params = JiraVelocityUtils.getDefaultVelocityParams(authenticationContext);
         ComponentAccessor.getApplicationProperties();
        params.put("action", this);
        params.put("velocityhelper", new JiraVelocityHelper(fieldManager));
        params.put("requestContext", new DefaultVelocityRequestContextFactory(applicationProperties).getJiraVelocityRequestContext());
/*        params.put("userformat", component);*/
        params.put("textutils", new TextUtils());
        return params;
    }
}
