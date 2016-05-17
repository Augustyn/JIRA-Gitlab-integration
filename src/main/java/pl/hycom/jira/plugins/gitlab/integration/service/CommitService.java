package pl.hycom.jira.plugins.gitlab.integration.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.hycom.jira.plugins.gitlab.integration.dao.ICommitDao;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import java.util.List;

/**
 * Created by Kamil Rogowski on 22.04.2016.
 */
@Service
@Log4j
public class CommitService implements ICommitService {

    //FIXME
    @Autowired
    private ICommitDao commitRepository;

    @Override
    public List<Commit> getNewCommits(int perPage, int pageNumber) {

        return commitRepository.getNewCommits(perPage, pageNumber);
    }

    @Override
    public Commit getOneCommit(String shaSum) {
        return commitRepository.getOneCommit(shaSum);
    }
}
