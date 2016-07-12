package ut.pl.hycom.jira.plugins.gitlab.integration.search;

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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.hycom.jira.plugins.gitlab.integration.dao.CommitRepository;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigEntity;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.search.CommitIndexer;
import pl.hycom.jira.plugins.gitlab.integration.search.LucenePathSearcher;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Damian Deska on 5/17/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class CommitIndexerTest {
    Logger log = LoggerFactory.getLogger(CommitIndexerTest.class);

    @InjectMocks private CommitIndexer commitIndexer = new CommitIndexer();
    @InjectMocks private CommitRepository commitRepository = new CommitRepository();

    @Mock private LucenePathSearcher lucenePathSearcher;
    @Mock private ConfigEntity config;

    private Commit commit = new Commit();
    private String urlMock = "https://gitlab.com/api/v3/projects/1063546/repository/commits";
    private String privateTokenMock = "KCi3MfkU7qNGJCe3pQUW";

    @Before
    public void setUp() {

    }

    @Test
    public void indexNewCommitTest() throws IOException {
        //before
        Mockito.when(config.getLink()).thenReturn("https://gitlab.com/");
        Mockito.when(config.getGitlabProjectId()).thenReturn(1063546);
        Mockito.when(config.getSecret()).thenReturn("KCi3MfkU7qNGJCe3pQUW");
        Mockito.when(config.getGitlabProjectId()).thenReturn(1063546);
        Mockito.when(lucenePathSearcher.getIndexPath()).thenReturn(Paths.get("./target/lucenetest/"));
        //when
        List<Commit> commitList = commitRepository.getNewCommits(config, 10, 1);
        int i = 1;
        //then
        for(Commit newCommit : commitList) {
            log.info(newCommit.getId() + ", " + newCommit.getAuthorName());
            newCommit.setMessage("PIP-" + i + ", " + newCommit.getMessage());
            i++;
            commitIndexer.indexFile(newCommit);
        }

    }

    @Test
    public void indexOneCommitTest() throws IOException{
        Mockito.when(config.getLink()).thenReturn("https://gitlab.com/");
        Mockito.when(config.getGitlabProjectId()).thenReturn(1063546);
        Mockito.when(config.getSecret()).thenReturn("KCi3MfkU7qNGJCe3pQUW");
        Mockito.when(config.getGitlabProjectId()).thenReturn(1063546);

        Commit oneCommit = commitRepository.getOneCommit(config, "79be5e2c5e6742d7513d11e0956138f4bf02ab3b");
        log.info(oneCommit.getId());
        commitIndexer.indexFile(oneCommit);
    }
}