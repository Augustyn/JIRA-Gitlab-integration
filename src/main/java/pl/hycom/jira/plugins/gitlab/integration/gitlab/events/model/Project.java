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
package pl.hycom.jira.plugins.gitlab.integration.gitlab.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
@Data
public class Project {

    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("web_url")
    public String webUrl;
    @JsonProperty("avatar_url")
    public Object avatarUrl;
    @JsonProperty("git_ssh_url")
    public String gitSshUrl;
    @JsonProperty("git_http_url")
    public String gitHttpUrl;
    @JsonProperty("namespace")
    public String namespace;
    @JsonProperty("visibility_level")
    public Integer visibilityLevel;
    @JsonProperty("path_with_namespace")
    public String pathWithNamespace;
    @JsonProperty("default_branch")
    public String defaultBranch;
    @JsonProperty("homepage")
    public String homepage;
    @JsonProperty("url")
    public String url;
    @JsonProperty("ssh_url")
    public String sshUrl;
    @JsonProperty("http_url")
    public String httpUrl;

}