package pl.hycom.jira.plugins.gitlab.integration.search;

/*
 * <p>Copyright (c) 2016, Damian Deska
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


import lombok.extern.log4j.Log4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Damian Deska on 5/17/16.
 */

@Log4j
@Service
public class CommitSearcher {

    private Analyzer analyzer = new StandardAnalyzer();
    public int hitsPerPage = 10;


    @Autowired
    private LucenePathSearcher lucenePathSearcher;

    public List<Document> searchCommits(String fieldName, String fieldValue) throws ParseException, IOException {

        List<Document> foundedCommitsList = new ArrayList<Document>();
        Path path = lucenePathSearcher.getIndexPath();
        try (Directory indexDirectory = FSDirectory.open(path)) {

            Query query = new QueryParser(fieldName, analyzer).parse(fieldValue);
            IndexReader reader = DirectoryReader.open(indexDirectory);
            IndexSearcher searcher = new IndexSearcher(reader);

            TopDocs docs = searcher.search(query, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;

            for (ScoreDoc hit : hits) {
                int docId = hit.doc;
                Document document = searcher.doc(docId);
                foundedCommitsList.add(document);
            }
        }


        return foundedCommitsList;
    }

    public List<Commit> searchCommitsByIssue(String jiraIssueKey) throws IOException {
        List<Commit> foundedCommitsList = new ArrayList<Commit>();
        Path path = lucenePathSearcher.getIndexPath();
        try(Directory indexDirectory = FSDirectory.open(path)) {

            WildcardQuery query = new WildcardQuery(new Term("message", jiraIssueKey));
            IndexReader reader = DirectoryReader.open(indexDirectory);
            IndexSearcher searcher = new IndexSearcher(reader);

            TopDocs docs = searcher.search(query, hitsPerPage);
            List<ScoreDoc> hitsList = Arrays.asList(docs.scoreDocs);

            hitsList.stream().forEach(hit -> {
                try {
                    int docId = hit.doc;
                    Document document = searcher.doc(docId);

                    Commit tmpCommit = new Commit.CommitBuilder()
                            .withId(document.get("id"))
                            .withShortId(document.get("short_id"))
                            .withTitle(document.get("title"))
                            .withAuthorName(document.get("author_name"))
                            .withAuthorEmail(document.get("author_email"))
                            .withCreatedAt(document.get("created_at"))
                            .withMessage(document.get("message"))
                            .build();

                    foundedCommitsList.add(tmpCommit);
                } catch(IOException e) {
                    throw new UncheckedIOException(e);
                }
            });

        }
        return foundedCommitsList;
    }

    public boolean checkIfCommitIsIndexed(String idValue) throws ParseException, IOException {
        Path path = lucenePathSearcher.getIndexPath();
        Directory indexDirectory = FSDirectory.open(path);
        Query query = new QueryParser("id", analyzer).parse(idValue);

        try (IndexReader reader = DirectoryReader.open(indexDirectory)) {

            IndexSearcher searcher = new IndexSearcher(reader);

            TopDocs docs = searcher.search(query, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;

            for (ScoreDoc hit : hits) {
                int docId = hit.doc;
                Document document = searcher.doc(docId);

                if (idValue.equals(document.get("id"))) {
                    reader.close();
                    return true;
                }
            }
        }

        return false;


    }

}
