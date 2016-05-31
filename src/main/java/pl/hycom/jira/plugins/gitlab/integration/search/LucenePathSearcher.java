package pl.hycom.jira.plugins.gitlab.integration.search;

/*
 * <p>Copyright (c) 2016, Damian Deska & Kamil Rogowski
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

import com.atlassian.jira.config.util.IndexPathManager;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Kamil Rogowski on 31.05.2016.
 */
@Log4j
@Service
public class LucenePathSearcher {

    @Autowired
    private IndexPathManager indexPathManager;

    private static final String COMMIT_INDEXER_DIRECTORY = "gitlab-integration";

    @PostConstruct
    public void init() {
        final boolean ifDirectoryExists = indexDirectoryExists();
        if(getIndexPath() == null){
            throw new NullPointerException("Cannot start plugin, indexPath is NULL");
        }
        if (!ifDirectoryExists) {
            final File file = new File(getIndexPathStr());
            file.mkdir();
        }
    }

    private boolean indexDirectoryExists() {
        try {
            File file = new File(getIndexPathStr());
            return file.exists();
        } catch (Exception e) {
            return false;
        }
    }


    private String getIndexPathStr() {
        String indexPath = null;
        String rootIndexPath = indexPathManager.getPluginIndexRootPath();
        if (rootIndexPath != null) {
            indexPath = rootIndexPath + System.getProperty("file.separator") + COMMIT_INDEXER_DIRECTORY;
        }

        return indexPath;
    }

    public Path getIndexPath(){
      return Paths.get(getIndexPathStr());
    }
}
