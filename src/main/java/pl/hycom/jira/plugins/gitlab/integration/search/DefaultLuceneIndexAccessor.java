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
package pl.hycom.jira.plugins.gitlab.integration.search;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NativeFSLockFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

import static pl.hycom.jira.plugins.gitlab.integration.search.LuceneCommitIndex.VERSION_LUCENE;

/**
 * An implementation that uses JIRA's LuceneUtils for its guts.
 */
@Service
@Slf4j
@NoArgsConstructor
public class DefaultLuceneIndexAccessor implements LuceneIndexAccessor, InitializingBean {
    private static final Analyzer analyzer = new StandardAnalyzer(VERSION_LUCENE);
    private final Object lock = new Object();
    private Directory directory;
    private IndexWriter indexWriter;
    private IndexReader indexReader;
    @Autowired private LucenePathSearcher lucenePathSearcher;
    boolean refresh = false;
    public IndexReader getIndexReader() throws IOException {
        if (indexReader == null) {
            Path path = lucenePathSearcher.getIndexPath();
            indexReader = IndexReader.open(getDirectory(path));
        }
        return indexReader;
    }

    private Directory getDirectory(Path path) throws IOException {
        if (directory == null) {
            directory = FSDirectory.open(path.toFile(), new NativeFSLockFactory()/*, new SimpleFSLockFactory()*/);
        }
        return directory;
    }

    public IndexWriter getIndexWriter() throws IOException {
        if (indexWriter == null) {
             createIndexWriter();
        }
        return indexWriter;
    }

    private void createIndexWriter() throws IOException {
        // Everything in this method copied from LuceneUtils
        final IndexWriterConfig indexWriterConfig = new IndexWriterConfig(VERSION_LUCENE, analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND); //Default
        //indexWriterConfig.setRAMBufferSizeMB(128.0); //Default 16.0
        Path path = lucenePathSearcher.getIndexPath();
        indexWriter = new IndexWriter(getDirectory(path), indexWriterConfig);
        final MergePolicy mergePolicy = indexWriterConfig.getMergePolicy();
        if (mergePolicy instanceof LogMergePolicy) {
            ((LogMergePolicy)mergePolicy).setUseCompoundFile(Boolean.TRUE);
        }
        if (!IndexReader.indexExists(directory)) {
            indexWriter.commit();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Initializing index accessor. Creating writer");
        createIndexWriter();
    }
}
