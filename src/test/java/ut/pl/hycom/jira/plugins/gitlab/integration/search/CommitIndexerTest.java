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
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.search.CommitFields;
import pl.hycom.jira.plugins.gitlab.integration.search.CommitIndex;
import pl.hycom.jira.plugins.gitlab.integration.search.DefaultLuceneIndexAccessor;
import pl.hycom.jira.plugins.gitlab.integration.search.LuceneCommitIndex;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@Log4j
@RunWith(MockitoJUnitRunner.class)
public class CommitIndexerTest {
    //dependencies:
    @Mock private DefaultLuceneIndexAccessor indexAccessor;
    @Mock private IndexWriter indexWriter;
    @Mock private IndexReader indexReader;
    @Mock private IndexSearcherFactory indexSearcherFactory;


    // Tested class:
    private CommitIndex index;

    @Before
    public void setUp() throws IOException {
        //indexAccessor = new DefaultLuceneIndexAccessor(searcher);
        index = new LuceneCommitIndex(indexAccessor);
        MockitoAnnotations.initMocks(index);

    }

    @Test
    public void shouldIndexOneCommit() throws IOException {
        //given
        Commit commit = new Commit()
                .withId("f1d2d2f924e986ac86fdf7b36c94bcdf32beec15")
                .withAuthorEmail("test@example.com")
                .withAuthorName("Test John")
                .withTitle("title: Zażółć gęślą jaźń")
                .withGitProject(6667L)
                .withIssueKey("TP-1")
                .withMessage("[TP-1] test issue 1. Test commit")
                .withShortId("f1d2d2")
                .withCreatedAt(LocalDateTime.now());
        when(indexAccessor.getIndexWriter()).thenReturn(indexWriter);
        //when
        index.index(commit);
        //then
        ArgumentCaptor<Document> documentCapt = ArgumentCaptor.forClass(Document.class);
        verify(indexAccessor.getIndexWriter()).addDocument(documentCapt.capture());

        Document documentUsed = documentCapt.getValue();

        assertEquals("f1d2d2f924e986ac86fdf7b36c94bcdf32beec15", documentUsed.getFieldable(CommitFields.ID.name()).stringValue());
        assertEquals("test@example.com", documentUsed.getFieldable(CommitFields.AUTHOR_EMAIL.name()).stringValue());
        assertEquals("Test John", documentUsed.getFieldable(CommitFields.AUTHOR_NAME.name()).stringValue());
        assertEquals("title: Zażółć gęślą jaźń", documentUsed.getFieldable(CommitFields.TITLE.name()).stringValue());
        assertEquals("6667", documentUsed.getFieldable(CommitFields.GIT_PROJECT_ID.name()).stringValue());
        assertEquals("TP-1", documentUsed.getFieldable(CommitFields.JIRA_ISSUE_KEY.name()).stringValue());
        assertEquals("[TP-1] test issue 1. Test commit", documentUsed.getFieldable(CommitFields.COMMIT_MESSAGE.name()).stringValue());
        assertEquals("f1d2d2", documentUsed.getFieldable(CommitFields.SHORT_ID.name()).stringValue());
    }

    @Test @Ignore
    public void shouldSearchForCommits() throws IOException {

        IndexSearcher indexSearcher = mock(IndexSearcher.class);
        when(indexSearcherFactory.create(any())).thenReturn(indexSearcher);

        TopDocs topDocs = mock(TopDocs.class);
        ArgumentCaptor<Query> queryCap = ArgumentCaptor.forClass(Query.class);
        when(indexSearcher.search(queryCap.capture(), anyInt())).thenReturn(topDocs);

        topDocs.scoreDocs = new ScoreDoc[2];
        Arrays.asList(createScoreDoc(0), createScoreDoc(3)).toArray(topDocs.scoreDocs);

        when(indexSearcher.doc(0)).thenReturn(createDocument(4));
        when(indexSearcher.doc(3)).thenReturn(createDocument(5));
        //when
        List<Document> docs = index.searchCommits("field name input", "field Value input");
        //then
        verify(indexAccessor).getIndexReader();
        verify(indexSearcherFactory).create(indexReader);
        assertEquals("field name input:fieldValue input", queryCap.getValue().toString());
        assertEquals(docs.size(), 2);
    }

    private Document createDocument(float boost) {
        Document doc = new Document();
        doc.setBoost(boost);
        return doc;
    }

    private ScoreDoc createScoreDoc(int docId) {
        return new ScoreDoc(docId,0);
    }

}