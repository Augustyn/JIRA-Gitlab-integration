package pl.hycom.jira.plugins.gitlab.integration.gitpanel.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.dao.ICommitDao;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl.Commit;

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
    public List<Commit> getAllCommits() {
      return commitRepository.getAllCommits();
    }

    @Override
    public Commit getOneCommit() {
        return commitRepository.getOneCommit();
    }
}
