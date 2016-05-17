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

//import jdk.internal.org.objectweb.asm.tree.analysis.Analyzer;
import lombok.extern.log4j.Log4j;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.search.IndexSearcher;

/**
 * Created by Damian Deska on 5/17/16.
 */

@Log4j
public class CommitSearcher {

    Analyzer analyzer = new StandardAnalyzer();
    Path path = Paths.get("lucynatesty");

    public List<Document> searchCommits(String fieldName, String fieldValue) throws ParseException, IOException {

        int hitsPerPage = 10;
        List<Document> foundedCommitsList = new ArrayList<Document>();

        Directory indexDirectory = FSDirectory.open(path);
        Query query = new QueryParser(fieldName, analyzer).parse(fieldValue);

        IndexReader reader = DirectoryReader.open(indexDirectory);
        IndexSearcher searcher = new IndexSearcher(reader);

        TopDocs docs = searcher.search(query, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        for(int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document document = searcher.doc(docId);
            foundedCommitsList.add(document);
        }
        reader.close();

        return foundedCommitsList;
    }

    public boolean checkIfCommitIsIndexed(String idValue) throws ParseException, IOException {

        Directory indexDirectory = FSDirectory.open(path);
        int hitsPerPage = 10;
        Query query = new QueryParser("id", analyzer).parse(idValue);

        IndexReader reader = DirectoryReader.open(indexDirectory);
        IndexSearcher searcher = new IndexSearcher(reader);

        TopDocs docs = searcher.search(query, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        for(int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document document = searcher.doc(docId);

            if(idValue.equals(document.get("id"))) {
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;



    }

}
