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
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigManagerDao;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigManagerDaoImpl;
import pl.hycom.jira.plugins.gitlab.integration.dao.ICommitDao;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kamil Rogowski on 22.04.2016.
 */
@Service
@Log4j
public class CommitService implements ICommitService {

    @Autowired
    private ConfigManagerDaoImpl dao;
    private ICommitDao commitRepository;

    private int perPage;

    @Override
    public List<Commit> getNewCommits(Long projectId, int pageNumber) throws SQLException {
        return commitRepository.getNewCommits(dao.getProjectConfig(projectId.intValue()), perPage, pageNumber);
    }

    @Override
    public Commit getOneCommit(Long projectId, String shaSum) throws SQLException {
        return commitRepository.getOneCommit(dao.getProjectConfig(projectId.intValue()), shaSum);
    }
}
