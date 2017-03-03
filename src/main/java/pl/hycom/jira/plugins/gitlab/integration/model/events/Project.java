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
package pl.hycom.jira.plugins.gitlab.integration.model.events;


import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

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