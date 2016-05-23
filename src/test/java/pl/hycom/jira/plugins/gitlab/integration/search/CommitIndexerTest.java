package pl.hycom.jira.plugins.gitlab.integration.search;

/*
 * <p>Copyright (c) 2016, Damian Deska
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import pl.hycom.jira.plugins.gitlab.integration.dao.CommitRepository;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.search.CommitIndexer;

import java.io.IOException;
import java.util.List;

/**
 * Created by Damian Deska on 5/17/16.
 */

@Log4j
@RunWith(MockitoJUnitRunner.class)
public class CommitIndexerTest {

    @InjectMocks
    private Commit commit = new Commit();
    private CommitIndexer commitIndexer = new CommitIndexer();
    private CommitRepository commitRepository = new CommitRepository();
    private String urlMock = "https://gitlab.com/api/v3/projects/1063546/repository/commits";
    private String privateTokenMock = "KCi3MfkU7qNGJCe3pQUW";

    @Test
    public void indexNewCommitTest() throws IOException {
        List<Commit> commitList = commitRepository.getNewCommits(urlMock, privateTokenMock, 10, 1);
        for(Commit newCommit : commitList) {
            log.info(newCommit.getId() + ", " + newCommit.getAuthor_name());
            commitIndexer.indexFile(newCommit);
        }

    }

    @Test
    public void indexOneCommitTest() throws IOException{
        Commit oneCommit = commitRepository.getOneCommit(urlMock, privateTokenMock, "master");
        log.info(oneCommit.getId());
        commitIndexer.indexFile(oneCommit);
    }

}