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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j
@Service
@RequiredArgsConstructor
public class LucenePathSearcher {

    private static final String COMMIT_INDEXER_DIRECTORY = "gitlab-integration";

    @Autowired private final IndexPathManager indexPathManager;

    private Path luceneIndexPath;

    @PostConstruct
    public void init() throws IOException {
        if(indexPathManager.getIndexRootPath() == null){
            throw new NullPointerException("Cannot start plugin, index root path is NULL");
        }
        Path path = Paths.get(indexPathManager.getPluginIndexRootPath(), COMMIT_INDEXER_DIRECTORY);
        if(path == null) {
               throw new InvalidPathException(getIndexPathStr(), "Index path doesn't exists");
        }
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

}
