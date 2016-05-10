package pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl;

import java.io.*;
import java.util.ArrayList;

//import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class CommitDataIndexer {
/*
    private IndexWriter indexWriter;

    private Document getDocument (CommitData commitData) throws IOException {
        Document document = new Document();

        Field contentField = new Field(LuceneConstants.CONTENTS, commitData.getCommitData());

        Field fileNameField = new Field(LuceneConstants.FILE_NAME, commitData.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED);

        Field filePathField = new Field(LuceneConstants. FILEPATH, commitData.getCanonicalPath(), Field.Store.YES, Field.Index.NOT_ANALYZED);

        document.add(contentField);
        document.add(fileNameField);
        document.add(filePathField);

        return document;
    }


    public Indexer(String indexDirectoryPath) throws IOException {

        Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));

        indexWriter = new IndexWriter(indexDirectory, new StandardAnalyzer(Version.LUCENE_36), true, IndexWriter.MaxFieldLength.UNLIMITED);
    }

    private void indexFile(CommitData commitData) throws IOException {

        Document document = getDocument(commitData);

        indexWriter.addDocument(document);

    }*/

}
