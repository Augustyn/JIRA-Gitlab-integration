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
package pl.hycom.jira.plugins.gitlab.integration.search;

import lombok.extern.log4j.Log4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Mapper. Converts {@link Commit} to Lucene {@link Document}
 * or, Lucene {@link Document} to {@link Commit}
 */
@Log4j
public class CommitMapper {

    private CommitMapper() {}

    public static Document getDocument(Commit commit) throws IOException {
        Document document = new Document();
        document.add(new Field(CommitFields.ID.name(), commit.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(CommitFields.SHORT_ID.name(), commit.getShortId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(CommitFields.TITLE.name(), commit.getTitle(), Field.Store.YES, Field.Index.ANALYZED));
        document.add(new Field(CommitFields.AUTHOR_NAME.name(), commit.getAuthorName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(CommitFields.AUTHOR_EMAIL.name(), commit.getAuthorEmail(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(CommitFields.CREATED.name(), DateTimeFormatter.ISO_DATE_TIME.format(commit.getCreatedAt()), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(CommitFields.COMMIT_MESSAGE.name(), commit.getMessage(), Field.Store.YES, Field.Index.ANALYZED));
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(commit.getIssueKey())) {
            document.add(new Field(CommitFields.JIRA_ISSUE_KEY.name(), commit.getIssueKey(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        }
        if (commit.getGitProjectID() != null) {
            document.add(new Field(CommitFields.GIT_PROJECT_ID.name(), String.valueOf(commit.getGitProjectID()), Field.Store.YES, Field.Index.NOT_ANALYZED));
        }
        return document;
    }

    public static Commit getCommit(Document document) {
        Commit commit = new Commit()
                .withId(document.get(CommitFields.ID.name()))
                .withShortId(document.get(CommitFields.SHORT_ID.name()))
                .withTitle(document.get(CommitFields.TITLE.name()))
                .withAuthorName(document.get(CommitFields.AUTHOR_NAME.name()))
                .withAuthorEmail(document.get(CommitFields.AUTHOR_EMAIL.name()))
                .withCreatedAt(LocalDateTime.parse(document.get(CommitFields.CREATED.name()),DateTimeFormatter.ISO_DATE_TIME))
                .withMessage(document.get(CommitFields.COMMIT_MESSAGE.name()))
                .withIssueKey(document.get(CommitFields.JIRA_ISSUE_KEY.name()));
        if (!"null".equals(document.get(CommitFields.GIT_PROJECT_ID.name()))) {
            commit.setGitProjectID(Long.valueOf(document.get(CommitFields.GIT_PROJECT_ID.name())));
        }
        return commit;
    }
}
