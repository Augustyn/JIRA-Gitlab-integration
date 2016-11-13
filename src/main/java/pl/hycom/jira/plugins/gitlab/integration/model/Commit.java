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
package pl.hycom.jira.plugins.gitlab.integration.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Base model for commit message from Gitlab
 */
@Data
public class Commit {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    /**
     * commit Sha1sum
     */
    @JsonProperty("id")
    private String id;

    /**
     * First characters of property id
     * @see Commit#id
     */
    @JsonProperty("short_id")
    private String shortId;
    private String title;
    /**
     * Full committer name, set with git config --[global|system|local] user.name "<name/>"
     */
    @JsonProperty("author_name")
    private String authorName;
    /**
     * Committer email, set with git config --[global|system|local] user.email "<email/>"
     */
    @JsonProperty("author_email")
    private String authorEmail;

    // example: 2015-05-21T20:57:28.000+02:00;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern= DATE_FORMAT)
    @JsonProperty("created_at")
    private Date createdAt;
    /**
     * Git commit message.
     */
    private String message;
    /**
     * JIRA issue Key, if found. Issue key, is search in field `message`. May be null.
     * @see Commit#message
     */
    @JsonProperty("parent_ids")
    private List<String> parentIds = new ArrayList<String>();

    @JsonProperty("committed_date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern= DATE_FORMAT)
    private Date committedDate;

    @JsonProperty("authored_date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern= DATE_FORMAT)
    private Date authoredDate;

    private Object status;
    /**
     * JIRA issue Key.
     */
    @Transient @Nullable
    private String issueKey;
    /**
     * Git project ID, if found. May be null.
     */
    @Transient @Nullable
    private Long gitProjectID;

    //Builder methods
    public Commit withId(String id) {
        this.id = id;
        return this;
    }

    public Commit withShortId(String shortId) {
        this.shortId = shortId;
        return this;
    }

    public Commit withTitle(String title) {
        this.title = title;
        return this;
    }

    public Commit withAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public Commit withAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
        return this;
    }

    public Commit withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Commit withMessage(String message) {
        this.message = message;
        return this;
    }

    public Commit withIssueKey(String issueKey) {
        this.issueKey = issueKey;
        return this;
    }

    public Commit withGitProject(Long project) {
        this.gitProjectID = project;
        return this;
    }
}

