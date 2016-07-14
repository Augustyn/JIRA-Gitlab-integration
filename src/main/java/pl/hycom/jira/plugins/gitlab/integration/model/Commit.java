
package pl.hycom.jira.plugins.gitlab.integration.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.annotation.Transient;

import java.util.Date;

/**
 * Base model for commit message from any DVCS
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
@Data
public class Commit {
    private String id;
    @JsonProperty("short_id")
    private String shortId;
    private String title;
    @JsonProperty("author_name")
    private String authorName;
    @JsonProperty("author_email")
    private String authorEmail;
    // example: 2015-05-21T20:57:28.000+02:00;
    @JsonProperty("created_at")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm.sssZ")
    private Date createdAt;
    private String message;
    @Transient
    private String issueKey;
    @Transient
    private Long gitProjectID;

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

