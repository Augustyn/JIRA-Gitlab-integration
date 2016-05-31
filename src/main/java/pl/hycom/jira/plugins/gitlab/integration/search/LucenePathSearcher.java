package pl.hycom.jira.plugins.gitlab.integration.search;

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
            //TODO
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
