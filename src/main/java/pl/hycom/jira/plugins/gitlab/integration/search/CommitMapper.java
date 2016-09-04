package pl.hycom.jira.plugins.gitlab.integration.search;

import lombok.extern.log4j.Log4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.springframework.stereotype.Service;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import java.io.IOException;

/**
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
@Log4j
public class CommitMapper {
    public static Document getDocument(Commit commit) throws IOException {
        Document document = new Document();
        document.add(new StringField(CommitFields.ID.name(), commit.getId(), Field.Store.YES));
        document.add(new StringField(CommitFields.SHORT_ID.name(), commit.getShortId(), Field.Store.YES));
        document.add(new TextField(CommitFields.TITLE.name(), commit.getTitle(), Field.Store.YES));
        document.add(new StringField(CommitFields.AUTHOR_NAME.name(), commit.getAuthorName(), Field.Store.YES));
        document.add(new TextField(CommitFields.AUTHOR_EMAIL.name(), commit.getAuthorEmail(), Field.Store.YES));
        document.add(new TextField(CommitFields.CREATED.name(), CommitFields.formatter.format(commit.getCreatedAt()), Field.Store.YES));
        document.add(new TextField(CommitFields.COMMIT_MESSAGE.name(), commit.getMessage(), Field.Store.YES));
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(commit.getIssueKey())) {
            document.add(new TextField(CommitFields.JIRA_ISSUE_KEY.name(), commit.getIssueKey(), Field.Store.YES));
        }
        document.add(new TextField(CommitFields.GIT_PROJECT_ID.name(), commit.getMessage(), Field.Store.YES));
        return document;
    }
}
