package pl.hycom.jira.plugins.gitlab.integration.model;

/**
 * Created by anon on 19.04.2016.
 */

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

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
    @JsonProperty("created_at")
    private String createdAt;
    private String message;
    private String issueKey;

    public Commit(CommitBuilder commitBuilder) {
        this.id = commitBuilder.id;
        this.shortId = commitBuilder.shortId;
        this.title = commitBuilder.title;
        this.authorName = commitBuilder.authorName;
        this.authorEmail = commitBuilder.authorEmail;
        this.createdAt = commitBuilder.createdAt;
        this.message = commitBuilder.message;
        this.issueKey = commitBuilder.issueKey;
    }

    public static class CommitBuilder {
        private String id;
        private String shortId;
        private String title;
        private String authorName;
        private String authorEmail;
        private String createdAt;
        private String message;
        private String issueKey;

        public CommitBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public CommitBuilder withShortId(String shortId) {
            this.shortId = shortId;
            return this;
        }

        public CommitBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public CommitBuilder withAuthorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public CommitBuilder withAuthorEmail(String authorEmail) {
            this.authorEmail = authorEmail;
            return this;
        }

        public CommitBuilder withCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CommitBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public CommitBuilder withIssueKey(String issueKey) {
            this.issueKey = issueKey;
            return this;
        }

        public Commit build() {
            return new Commit(this);
        }

    }
}
