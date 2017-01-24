package pl.hycom.jira.plugins.gitlab.integration.search;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;

/**
 * Created by jkonczak on 1/24/17.
 */
public class IndexSearcherFactory {
    public IndexSearcher create(IndexReader reader) {
        return new IndexSearcher(reader);
    }
}
