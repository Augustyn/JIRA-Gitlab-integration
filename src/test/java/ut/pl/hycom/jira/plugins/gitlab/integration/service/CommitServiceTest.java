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
package ut.pl.hycom.jira.plugins.gitlab.integration.service;

import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import pl.hycom.jira.plugins.gitlab.integration.dao.CommitRepository;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigEntity;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.util.TemplateFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

@Log4j
@RunWith(MockitoJUnitRunner.class)
public class CommitServiceTest {

    @InjectMocks private CommitRepository commitService = new CommitRepository();

    @Mock private ConfigEntity config;
    @Spy private TemplateFactory template = new TemplateFactory();

    private String urlMock = "https://gitlab.com/";
    private String privateTokenMock = "R4jTxnBBT5Tr4xoSo7vw";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(commitService);
        Mockito.when(config.getGitlabURL()).thenReturn(urlMock);
        Mockito.when(config.getGitlabProjectId()).thenReturn(1063546L);
        Mockito.when(config.getGitlabSecretToken()).thenReturn(privateTokenMock);
        /*template = new TemplateFactory();*/
        /*Mockito.when(template.getRestTemplate()).thenReturn(new TemplateFactory().getRestTemplate());*/
        //ReflectionTestUtils.setField(commitService, "restTemplate", template);
    }

    @Test
    public void testGetNewCommits() throws Exception {
        List<Commit> commits = commitService.getNewCommits(config, null);
        assertThat("Commits size should be as ", commits.size(), is(equalTo(20)));
    }

    @Test
    public void testGetOneCommit() throws Exception {
        Collections.unmodifiableCollection(new ArrayList<>());
        String id = "3fabe415af4ae792ce1732c0921330a4832457a2";
        Commit commit = commitService.getOneCommit(config, id);
        assertThat("CommitEvent id's should be the same: ", commit.getId(), is(equalTo(id)));
    }
}
