package pl.hycom.jira.plugins.gitlab.integration.search;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSLockFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static pl.hycom.jira.plugins.gitlab.integration.search.LuceneCommitIndex.VERSION_LUCENE;

/**
 * An implementation that uses JIRA's LuceneUtils for its guts.
 */
@Service
@Slf4j
class DefaultLuceneIndexAccessor implements LuceneIndexAccessor {

    public IndexReader getIndexReader(Path path) throws IOException {
        return IndexReader.open(getDirectory(path));
    }

    private Directory getDirectory(Path path) throws IOException {
        return FSDirectory.open(path.toFile(), new SimpleFSLockFactory());
    }

    public IndexWriter getIndexWriter(Path path, Analyzer analyzer) throws IOException {
        // Everything in this method copied from LuceneUtils
        final IndexWriterConfig indexWriterConfig = new IndexWriterConfig(VERSION_LUCENE, analyzer);
        final IndexWriter indexWriter = new IndexWriter(FSDirectory.open(path.toFile()), indexWriterConfig);
        final MergePolicy mergePolicy = indexWriterConfig.getMergePolicy();
        if (mergePolicy instanceof LogMergePolicy) {
            ((LogMergePolicy)mergePolicy).setUseCompoundFile(Boolean.TRUE);
        }
        return indexWriter;
    }

    /**
     * Create a directory (robustly) or throw appropriate Exception
     * It's redundant, as {@link LucenePathSearcher} does the same: validates path. However unlike {@link LucenePathSearcher} it does it every time.
     * Not only on initialization.
     * @param path Lucene index directory path
     * @throws IOException if cannot create directory, write to the directory, or param 'path' is not a directory
     */
    private static void createDirRobust(final Path path) throws IOException {
        final File potentialPath = path.toFile();
        if (!potentialPath.exists()) {
            log.warn("Directory " + path + " does not exist - perhaps it was deleted?  Creating..");
            final boolean created = potentialPath.mkdirs();
            if (!created) {
                log.warn("Directory " + path + " could not be created.  Aborting index creation");
                throw new IOException("Could not create directory: " + path);
            }
        }
        if (!potentialPath.isDirectory()) {
            log.warn("File " + path + " is not a directory.  Cannot create index");
            throw new IOException("File " + path + " is not a directory.  Cannot create index");
        }
        if (!potentialPath.canWrite()) {
            log.warn("Dir " + path + " is not writable.  Cannot create index");
            throw new IOException("Dir " + path + " is not writable.  Cannot create index");
        }
    }
}
