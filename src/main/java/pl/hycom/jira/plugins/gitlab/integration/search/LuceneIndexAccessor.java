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
