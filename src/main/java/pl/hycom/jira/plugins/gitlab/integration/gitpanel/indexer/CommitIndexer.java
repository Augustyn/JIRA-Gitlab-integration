package pl.hycom.jira.plugins.gitlab.integration.gitpanel.indexer;

//TODO when Commit done

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

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.util.Version;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.service.CommitService;

/**
 * Created by Damian Deska on 25.04.2016.
 */

public class CommitIndexer {

    private IndexWriter indexWriter;
    private String fileNameMock = "fileName";
    private String filePathMock = "C:\\Lucene\\Indexer";

    //FIXME konstruktor do poprawy
    public CommitIndexer(String indexDirectoryPath) throws IOException {

        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        Path path = Paths.get("/tmp/testindex");
        Directory indexDirectory = FSDirectory.open(path);
        indexWriter = new IndexWriter(indexDirectory, indexWriterConfig);

        //indexWriter = new IndexWriter(indexDirectory, new StandardAnalyzer(Version.LUCENE_6_0_0), true, IndexWriter.MaxFieldLength.UNLIMITED);
    }

    private Document getDocument (CommitService Commit) throws IOException {
        Document document = new Document();

        //FIXME poczekac na odpowiednia metode
        //Field contentField = new Field(LuceneConstants.CONTENTS, CommitService.getOneCommit());

//        Field fileNameField = new Field(LuceneConstants.FILE_NAME, fileNameMock, Field.Store.YES, Field.Index.NOT_ANALYZED);
//
//        Field filePathField = new Field(LuceneConstants.FILE_PATH, filePathMock, Field.Store.YES, Field.Index.NOT_ANALYZED);
//
//        document.add(contentField);
//        document.add(fileNameField);
//        document.add(filePathField);

        return document;
    }


    private void indexFile(CommitService Commit) throws IOException {

        Document document = getDocument(Commit);

        indexWriter.addDocument(document);

    }
}
