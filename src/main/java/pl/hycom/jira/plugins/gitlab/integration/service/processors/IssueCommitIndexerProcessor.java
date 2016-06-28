package pl.hycom.jira.plugins.gitlab.integration.service.processors;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.exceptions.ProcessException;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.search.CommitIndexer;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Created by Thorgal on 21.06.2016.
 */
@Component
@Log4j
public class IssueCommitIndexerProcessor implements ProcessorInterface{

    @Autowired
    CommitIndexer commitIndexer;
    @Override
    public void execute(@NotNull Commit commitInfo) throws ProcessException {
        try {
            commitIndexer.indexFile(commitInfo);
        } catch (IOException e) {
            log.info("Failed to index commit with id: "+commitInfo.getId() + e.getMessage());
        }
    }
}
