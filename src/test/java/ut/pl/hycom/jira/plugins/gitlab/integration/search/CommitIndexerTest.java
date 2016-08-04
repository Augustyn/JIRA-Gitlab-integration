package ut.pl.hycom.jira.plugins.gitlab.integration.search;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import pl.hycom.jira.plugins.gitlab.integration.dao.CommitRepository;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigEntity;
import pl.hycom.jira.plugins.gitlab.integration.interceptor.RestLoggingInterceptor;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.search.CommitIndexer;
import pl.hycom.jira.plugins.gitlab.integration.search.CommitSearcher;
import pl.hycom.jira.plugins.gitlab.integration.search.LucenePathSearcher;
import pl.hycom.jira.plugins.gitlab.integration.util.TemplateFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
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
 *
 */
@Log4j
@RunWith(MockitoJUnitRunner.class)
public class CommitIndexerTest {
    @InjectMocks private CommitIndexer commitIndexer = new CommitIndexer();
    @InjectMocks private CommitRepository commitRepository = new CommitRepository();
    @InjectMocks private CommitSearcher commitSearcher = new CommitSearcher();

    @Mock private TemplateFactory restTemplateFactory;
    @Mock private LucenePathSearcher lucenePathSearcher;
    @Mock private ConfigEntity config;
    @Mock private RestTemplate template;

    private Commit commit = new Commit();
    @Before
    public void setUp() {
        commit = new Commit().withAuthorEmail("test@example.com").withAuthorName("Test John").withTitle("title")
                .withGitProject(6667L).withId("f1d2d2f924e986ac86fdf7b36c94bcdf32beec15").withIssueKey("TP-1")
                .withMessage("[TP-1] test issue 1. Test commit").withCreatedAt(new Date()).withShortId("f1d2d2");
    }

    /**
     * "test suite", because one cannot set order of tests in JUnit.
     */
    @Test
    public void indexingTestSuite() throws IOException, ParseException {
        //before
        Mockito.when(config.getLink()).thenReturn("https://gitlab.com/");
        Mockito.when(config.getGitlabProjectId()).thenReturn(1063546);
        Mockito.when(config.getSecret()).thenReturn("KCi3MfkU7qNGJCe3pQUW");
        Mockito.when(config.getGitlabProjectId()).thenReturn(1063546);
        Mockito.when(lucenePathSearcher.getIndexPath()).thenReturn(Paths.get("./target/lucenetest/"));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new RestLoggingInterceptor()));
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        Mockito.when(restTemplateFactory.getRestTemplate()).thenReturn(restTemplate);
        //when // TODO: this test 'suite' has too much responsibility: getting new commits, indexing them, reading index. Break to smaller unit tests
        this.indexOneCommitTest();
        this.indexNewCommitTest();
        this.checkIfCommitIsIndexedTest();
        this.searchCommitsTest();
    }

    public void indexNewCommitTest() throws IOException {

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

    public void indexOneCommitTest() throws IOException{
        Commit oneCommit = commitRepository.getOneCommit(config, "79be5e2c5e6742d7513d11e0956138f4bf02ab3b");
        assertThat("Commit cannot be null(!)", oneCommit, notNullValue());
        log.info("Will try to index commit: " + oneCommit);
        commitIndexer.indexFile(oneCommit);
    }

    public void searchCommitsTest() throws ParseException, IOException {
        Mockito.when(lucenePathSearcher.getIndexPath()).thenReturn(Paths.get("./target/lucenetest/"));

        String fieldName = "author_name";
        String fieldValue = "kamilrogowski";
        List<Document> foundedCommitsList =  commitSearcher.searchCommits(fieldName, fieldValue);

        for(Document document : foundedCommitsList) {
            Assert.assertTrue(document.get("author_name").equals("kamilrogowski"));
        }

    }


    public void checkIfCommitIsIndexedTest() throws ParseException, IOException {
        Mockito.when(lucenePathSearcher.getIndexPath()).thenReturn(Paths.get("./target/lucenetest/"));
        String validIdValue = "da3d482b7a675926502c20b0598b470f05ae8c57";
        String invalidIdValue = "xxx";

        Assert.assertTrue(commitSearcher.checkIfCommitIsIndexed(validIdValue));
        Assert.assertFalse(commitSearcher.checkIfCommitIsIndexed(invalidIdValue));
    }
}