package pl.hycom.jira.plugins.gitlab.integration.gitlab.events.model;
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

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * <p>Copyright (c) 2016, Hycom S.A.
 * Project:  gitlab-integration.</p>
 * <p>All rights reserved.</p>
 *
 * @author Augustyn Kończak <mailto:augustyn.konczak@hycom.pl> on 23.06.16.
 */
@Generated("org.jsonschema2pojo")
@Data
public class Repository {
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("homepage")
    @Expose
    public String homepage;
    @SerializedName("git_http_url")
    @Expose
    public String gitHttpUrl;
    @SerializedName("git_ssh_url")
    @Expose
    public String gitSshUrl;
    @SerializedName("visibility_level")
    @Expose
    public Integer visibilityLevel;
}
