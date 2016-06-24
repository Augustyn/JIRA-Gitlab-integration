package pl.hycom.jira.plugins.gitlab.integration.gitlab.events;
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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.validation.Valid;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import pl.hycom.jira.plugins.gitlab.integration.gitlab.events.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.gitlab.events.model.Project;
import pl.hycom.jira.plugins.gitlab.integration.gitlab.events.model.Repository;

/**
 * <p>Copyright (c) 2016, Hycom S.A.
 * Project:  gitlab-integration.</p>
 * <p>All rights reserved.</p>
 *
 * @author Augustyn Kończak <mailto:augustyn.konczak@hycom.pl> on 23.06.16.
 */

@Generated("org.jsonschema2pojo")
@Data
public class Push {

    @SerializedName("object_kind")
    @Expose
    public String objectKind;
    @SerializedName("before")
    @Expose
    public String before;
    @SerializedName("after")
    @Expose
    public String after;
    @SerializedName("ref")
    @Expose
    public String ref;
    @SerializedName("checkout_sha")
    @Expose
    public String checkoutSha;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("user_email")
    @Expose
    public String userEmail;
    @SerializedName("user_avatar")
    @Expose
    public String userAvatar;
    @SerializedName("project_id")
    @Expose
    public Integer projectId;
    @SerializedName("project")
    @Expose
    @Valid
    public Project project;
    @SerializedName("repository")
    @Expose
    @Valid
    public Repository repository;
    @SerializedName("commits")
    @Expose
    @Valid
    public List<Commit> commits = new ArrayList<Commit>();
    @SerializedName("total_commits_count")
    @Expose
    public Integer totalCommitsCount;

}
