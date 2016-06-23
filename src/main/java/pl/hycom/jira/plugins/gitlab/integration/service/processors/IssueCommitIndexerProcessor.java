package pl.hycom.jira.plugins.gitlab.integration.service.processors;

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
public class IssueCommitIndexerProcessor implements ProcessorInterface{

    @Override
    public void execute(@NotNull Commit commitInfo) throws ProcessException {
        CommitIndexer commitIndexer = new CommitIndexer();
        try {
            commitIndexer.indexFile(commitInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
