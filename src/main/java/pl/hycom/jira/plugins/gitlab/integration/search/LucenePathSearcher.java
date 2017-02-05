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

import com.atlassian.jira.config.util.IndexPathManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject)) //Inject all final variables.
public class LucenePathSearcher {

    private final IndexPathManager indexPathManager;

    private static final String COMMIT_INDEXER_DIRECTORY = "gitlab-integration";
    private Path luceneIndexPath;

    @PostConstruct
    public void init() throws IOException {
        if(indexPathManager.getIndexRootPath() == null){
            throw new NullPointerException("Cannot start plugin, index root path is NULL");
        }
        Path path = Paths.get(indexPathManager.getPluginIndexRootPath(), COMMIT_INDEXER_DIRECTORY);
        createDirRobust(path);
        luceneIndexPath = path;
        if (!luceneIndexPath.toFile().exists()) {
            final boolean created = luceneIndexPath.toFile().mkdirs();
            if (!created && !path.toFile().exists() && !path.toFile().canWrite() && !path.toFile().isDirectory()) {
                throw new IOException(String.format("Index Path: '%s' does not exist: '%b', cannot be created: '%b', " +
                                "or is not writable: '%b', is directory? '%b'. Cannot index.", path,
                        path.toFile().exists(), created, path.toFile().canWrite(), path.toFile().isDirectory()));
            }
        }
    }

    private String getIndexPathStr() {
        return luceneIndexPath.toString();
    }

    public Path getIndexPath(){
        return luceneIndexPath;
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
