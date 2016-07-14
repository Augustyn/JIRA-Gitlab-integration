package pl.hycom.jira.plugins.gitlab.integration.scheduler;

import com.atlassian.scheduler.JobRunner;
import com.atlassian.scheduler.config.JobRunnerKey;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.impl.AwesomePluginJobRunnerImpl;

/**
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
 *
 * @since v1.0
 */
public interface AwesomePluginJobRunner extends JobRunner
{
    /** Our job runner key */
    JobRunnerKey AWESOME_JOB = JobRunnerKey.of(AwesomePluginJobRunnerImpl.class.getName());

    /** Name of the parameter map entry where the ID is stored */
    String AWESOME_ID = "awesomeId";
}
