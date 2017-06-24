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

import com.atlassian.jira.plugin.issuetabpanel.IssueAction;
import com.atlassian.jira.template.VelocityTemplatingEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.velocity.exception.VelocityException;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import java.time.Instant;
import java.util.*;

import static com.atlassian.jira.template.TemplateSources.file;

/**
 * Basic "action" that is displayed in gitTabPanel for given commit.
 */
@Log4j
@RequiredArgsConstructor
public class GitCommitsAction implements IssueAction {
    private static final String PLUGIN_TEMPLATE = "templates/tabpanels/git-tab-content.vm";
    private final List<Commit> commits;
    private final Map<String,Object> params;
    private final VelocityTemplatingEngine templateEngine;
    @Override
    public Date getTimePerformed() {
        final Optional<Commit> firstCommit = Optional.ofNullable(commits).orElse(Collections.emptyList()).stream().findFirst();
        if (firstCommit.isPresent()) {
            return Date.from(Instant.from(firstCommit.get().getCreatedAt()));
        } else {
            return new Date();
        }
    }

    @Override
    public String getHtml() {
        try {
            return templateEngine.render(file(PLUGIN_TEMPLATE)).applying(params).asHtml();
        } catch (VelocityException e) {
            log.error("Error while rendering velocity template for '" + PLUGIN_TEMPLATE + "' with commits: " + commits, e);
            return "Velocity template generation failed.";
        }
    }

    @Override
    public boolean isDisplayActionAllTab() {
        return Boolean.FALSE;
    }

}
