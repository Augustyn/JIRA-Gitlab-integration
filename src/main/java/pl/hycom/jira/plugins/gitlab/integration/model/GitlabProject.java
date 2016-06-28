package pl.hycom.jira.plugins.gitlab.integration.model;
/*
 * <p>Copyright (c) 2016, Authors
 * Project:  gitlab-integration.</p>
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

import lombok.Data;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "gitlabProjectName",
        "gitlabProjectId"
})
public class GitlabProject {

    @JsonProperty("gitlabProjectName")
    private String gitlabProjectName;
    @JsonProperty("gitlabProjectId")
    private Integer gitlabProjectId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The gitlabProjectName
     */
    @JsonProperty("gitlabProjectName")
    public String getGitlabProjectName() {
        return gitlabProjectName;
    }

    /**
     *
     * @param gitlabProjectName
     * The gitlabProjectName
     */
    @JsonProperty("gitlabProjectName")
    public void setGitlabProjectName(String gitlabProjectName) {
        this.gitlabProjectName = gitlabProjectName;
    }

    /**
     *
     * @return
     * The gitlabProjectId
     */
    @JsonProperty("gitlabProjectId")
    public Integer getGitlabProjectId() {
        return gitlabProjectId;
    }

    /**
     *
     * @param gitlabProjectId
     * The gitlabProjectId
     */
    @JsonProperty("gitlabProjectId")
    public void setGitlabProjectId(Integer gitlabProjectId) {
        this.gitlabProjectId = gitlabProjectId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}