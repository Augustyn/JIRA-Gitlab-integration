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
package pl.hycom.jira.plugins.gitlab.integration.service.impl;

import com.atlassian.jira.issue.Issue;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigEntity;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigManagerDao;
import pl.hycom.jira.plugins.gitlab.integration.dao.ICommitDao;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.search.CommitIndex;
import pl.hycom.jira.plugins.gitlab.integration.service.ICommitService;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Inject)) //Inject all final variables.
public class CommitService implements ICommitService {

    private final ConfigManagerDao dao;
    private final ICommitDao commitRepository;
    private final CommitIndex commitSearcher;
    //TODO: get me from gitlab config:

    @Override
    public List<Commit> getNewCommits(Long projectId) throws SQLException, IOException {
        final ConfigEntity projectConfig = dao.getProjectConfig(projectId);
        return getNewCommits(projectConfig);
    }

    @Override
    public List<Commit> getNewCommits(ConfigEntity config) throws SQLException, IOException {
        boolean breakLoop = false;
        List<Commit> resultList = new ArrayList<>();
        LocalDateTime date = null;
        do {
            final List<Commit> commitList = commitRepository.getNewCommits(config, date);
            if (commitList.isEmpty()) {
                breakLoop = true;
            }
            for(Commit commit : commitList) {
                date = date == null ? commit.getCreatedAt() : date.isBefore(commit.getCreatedAt()) ? date : commit.getCreatedAt();
                if(commitSearcher.checkIfCommitIsIndexed(commit.getId()) || resultList.contains(commit)) {
                    breakLoop = true;
                } else {
                    resultList.add(commit);
                }
            }
        } while(!breakLoop);
        return resultList;
    }

    @Override
    public List<Commit> getAllIssueCommits(Issue jiraIssue) throws IOException {
        String issueKey = jiraIssue.getKey();
        return commitSearcher.searchCommitsByIssue(issueKey);
    }

    @Override
    public Commit getOneCommit(Long projectId, String shaSum) throws SQLException, IOException {
        return commitRepository.getOneCommit(dao.getProjectConfig(projectId), shaSum);
    }
}
