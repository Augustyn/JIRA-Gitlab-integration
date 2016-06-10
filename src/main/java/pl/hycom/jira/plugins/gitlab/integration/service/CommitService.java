package pl.hycom.jira.plugins.gitlab.integration.service;
/*
 * <p>Copyright (c) 2016, Damian Deska, Kamil Rogowski
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


import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.hycom.jira.plugins.gitlab.integration.dao.ICommitDao;
import pl.hycom.jira.plugins.gitlab.integration.listeners.Scheduler;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import com.atlassian.sal.api.scheduling.PluginJob;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Kamil Rogowski on 22.04.2016.
 */
@Service
@Log4j
public class CommitService implements ICommitService, PluginJob{


    @Autowired
    private ICommitDao commitRepository;

    @Override
    public List<Commit> getNewCommits(String repositoryUrl, String privateToken, int perPage, int pageNumber) {

        return commitRepository.getNewCommits(repositoryUrl, privateToken, perPage, pageNumber);
    }

    @Override
    public Commit getOneCommit(String repositoryUrl, String privateToken, String shaSum) {
        return commitRepository.getOneCommit(repositoryUrl, privateToken, shaSum);
    }

    @Override
    public void execute(Map<String, Object> map) {
        final Scheduler job = (Scheduler) map.get(Scheduler.getKEY());
        assert job != null;
        job.setLastRun(new Date());
        log.debug("Execute recurring issue job");
    }
}

