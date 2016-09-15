package pl.hycom.jira.plugins.gitlab.integration.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author higashi on 2016-09-05, HYCOM S.A.
 */
public interface LuceneIndexAccessor {
    /**
     * Gets a Lucene {@link org.apache.lucene.index.IndexReader} at the given path.
     *
     * @param path the path.
     * @return the IndexReader.
     * @throws IOException if there's some problem getting the reader.
     */
    IndexReader getIndexReader(Path path) throws IOException;

    /**
     * Gets a Lucene {@link org.apache.lucene.index.IndexWriter} at the given path.
     *
     * @param path     the path.
     * @param analyzer the {@link org.apache.lucene.analysis.Analyzer} to use.
     * @return the IndexWriter.
     * @throws IOException if there's some problem getting the writer.
     */
    IndexWriter getIndexWriter(Path path, Analyzer analyzer) throws IOException;

}
