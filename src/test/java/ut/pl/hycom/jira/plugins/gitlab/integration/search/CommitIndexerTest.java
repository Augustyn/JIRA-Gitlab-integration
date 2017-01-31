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
package ut.pl.hycom.jira.plugins.gitlab.integration.search;

import lombok.extern.log4j.Log4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.search.CommitFields;
import pl.hycom.jira.plugins.gitlab.integration.search.CommitIndex;
import pl.hycom.jira.plugins.gitlab.integration.search.LuceneCommitIndex;
import pl.hycom.jira.plugins.gitlab.integration.search.LuceneIndexAccessor;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Log4j
@RunWith(MockitoJUnitRunner.class)
public class CommitIndexerTest {
    @InjectMocks
    private CommitIndex index = new LuceneCommitIndex();

    @Mock
    private LuceneIndexAccessor indexAccessor;
    @Mock
    private IndexWriter indexWriter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(index);
    }

    @Test
    public void shouldIndexOneCommit() throws IOException {
        when(indexAccessor.getIndexWriter()).thenReturn(indexWriter);
        //when
        Commit commit = new Commit()
                .withId("f1d2d2f924e986ac86fdf7b36c94bcdf32beec15")
                .withAuthorEmail("test@example.com")
                .withAuthorName("Test John")
                .withTitle("title")
                .withGitProject(6667L)
                .withIssueKey("TP-1")
                .withMessage("[TP-1] test issue 1. Test commit")
                .withShortId("f1d2d2")
                .withCreatedAt(LocalDateTime.now());

        index.indexFile(commit);
        //then
        ArgumentCaptor<Document> documentCapt = ArgumentCaptor.forClass(Document.class);
        verify(indexWriter).addDocument(documentCapt.capture());

        Document documentUsed = documentCapt.getValue();

        assertEquals("f1d2d2f924e986ac86fdf7b36c94bcdf32beec15", documentUsed.getFieldable(CommitFields.ID.name()).stringValue());
        assertEquals("test@example.com", documentUsed.getFieldable(CommitFields.AUTHOR_EMAIL.name()).stringValue());
        assertEquals("Test John", documentUsed.getFieldable(CommitFields.AUTHOR_NAME.name()).stringValue());
        assertEquals("title", documentUsed.getFieldable(CommitFields.TITLE.name()).stringValue());
        assertEquals("6667", documentUsed.getFieldable(CommitFields.GIT_PROJECT_ID.name()).stringValue());
        assertEquals("TP-1", documentUsed.getFieldable(CommitFields.JIRA_ISSUE_KEY.name()).stringValue());
        assertEquals("[TP-1] test issue 1. Test commit", documentUsed.getFieldable(CommitFields.COMMIT_MESSAGE.name()).stringValue());
        assertEquals("f1d2d2", documentUsed.getFieldable(CommitFields.SHORT_ID.name()).stringValue());
    }

}