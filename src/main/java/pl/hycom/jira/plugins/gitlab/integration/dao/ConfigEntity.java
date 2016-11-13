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
package pl.hycom.jira.plugins.gitlab.integration.dao;

import net.java.ao.RawEntity;
import net.java.ao.schema.AutoIncrement;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.PrimaryKey;

public interface ConfigEntity extends RawEntity<Long> {
    @NotNull
    @AutoIncrement
    @PrimaryKey("ProjectID")
    public Long getProjectID();

    public String getGitlabURL();
    public void setGitlabURL(String gitlabUrl);

    public String getGitlabSecretToken();
    public void setGitlabSecretToken(String secret);

    public String getClientId();
    public void setClientId(String clientId);

    public String getGitlabProjectName();
    public void setGitlabProjectName(String gitlabProjectName);

    public Long getGitlabProjectId();
    public void setGitlabProjectId(Long gitlabProjectId);

}
