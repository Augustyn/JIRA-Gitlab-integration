package pl.hycom.jira.plugins.gitlab.integration.search;

import lombok.extern.log4j.Log4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

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
@Service
public class LuceneCommitIndex implements CommitIndex {

    @Autowired
    private LucenePathSearcher lucenePathSearcher;

    private Analyzer analyzer = new StandardAnalyzer();

    @Override
    public void indexFile(Commit commit) throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        Path path = lucenePathSearcher.getIndexPath();

        Directory indexDirectory = FSDirectory.open(path);
        IndexWriter indexWriter = new IndexWriter(indexDirectory, indexWriterConfig);
        Document document = CommitMapper.getDocument(commit);
        indexWriter.addDocument(document);
        indexWriter.close();
    }


    public List<Document> searchCommits(String fieldName, String fieldValue) throws ParseException, IOException {

        List<Document> foundedCommitsList = new ArrayList<Document>();
        Path path = lucenePathSearcher.getIndexPath();
        try (Directory indexDirectory = FSDirectory.open(path)) {

            Query query = new QueryParser(fieldName, analyzer).parse(fieldValue);
            IndexReader reader = DirectoryReader.open(indexDirectory);
            IndexSearcher searcher = new IndexSearcher(reader);

            TopDocs docs = searcher.search(query, HITS_PER_PAGE);
            ScoreDoc[] hits = docs.scoreDocs;

            for (ScoreDoc hit : hits) {
                int docId = hit.doc;
                Document document = searcher.doc(docId);
                foundedCommitsList.add(document);
            }
        }


        return foundedCommitsList;
    }

    public List<Commit> searchCommitsByIssue(String jiraIssueKey) {
        try(Directory indexDirectory = FSDirectory.open(lucenePathSearcher.getIndexPath())) {
            WildcardQuery query = new WildcardQuery(new Term(CommitFields.COMMIT_MESSAGE.name(), jiraIssueKey));
            IndexReader reader = DirectoryReader.open(indexDirectory);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(query, HITS_PER_PAGE);
            return Arrays.stream(docs.scoreDocs).map(hit -> {
                try {
                    Document document = searcher.doc(hit.doc);
                    return new Commit()
                            .withId(document.get(CommitFields.ID.name()))
                            .withShortId(document.get(CommitFields.SHORT_ID.name()))
                            .withTitle(document.get(CommitFields.TITLE.name()))
                            .withAuthorName(document.get(CommitFields.AUTHOR_NAME.name()))
                            .withAuthorEmail(document.get(CommitFields.AUTHOR_EMAIL.name()))
                            .withCreatedAt(CommitFields.formatter.parse(document.get(CommitFields.CREATED.name())))
                            .withMessage(document.get(CommitFields.COMMIT_MESSAGE.name()))
                            .withIssueKey(document.get(CommitFields.JIRA_ISSUE_KEY.name()))
                            .withGitProject(Long.valueOf(document.get(CommitFields.GIT_PROJECT_ID.name())));
                } catch(IOException | java.text.ParseException e) {
                    log.warn("Failed to recreate Commit from lucene doc Id: " + hit.doc , e);
                    return null;
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            log.warn("Failed to search commits by JIRA issue key: " + jiraIssueKey + " with message: " + e.getMessage());
            log.debug("Stack: ", e);
        }
        return Collections.emptyList();
    }

    public boolean checkIfCommitIsIndexed(String idValue) throws ParseException, IOException {
        Path path = lucenePathSearcher.getIndexPath();
        Directory indexDirectory = FSDirectory.open(path);
        Query query = new QueryParser(CommitFields.ID.name(), analyzer).parse(idValue);

        try (IndexReader reader = DirectoryReader.open(indexDirectory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(query, HITS_PER_PAGE);

            Optional<ScoreDoc> firstDoc = Arrays.stream(docs.scoreDocs).filter(hit -> {
                try {
                    Document document = searcher.doc(hit.doc);
                    log.debug("Found: " + document);
                    if (idValue.equals(document.get(CommitFields.ID.name()))) {
                        return true;
                    }
                } catch (IOException e) {
                    log.warn("Failed to search for hit: " + hit.doc + " with message: " + e.getMessage());
                }
                return false;
            }).findFirst();
            return firstDoc.isPresent();
        }
    }

}
