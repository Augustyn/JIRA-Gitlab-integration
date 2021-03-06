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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.search.DefaultLuceneIndexAccessor;
import pl.hycom.jira.plugins.gitlab.integration.search.LucenePathSearcher;
import pl.hycom.jira.plugins.gitlab.integration.service.ProcessorManager;
import pl.hycom.jira.plugins.gitlab.integration.service.processors.ProcessorInterface;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Tests of processor manager.
 */
@Log4j
@RunWith(MockitoJUnitRunner.class)
public class ProcessorManagerTest {

    @Mock
    private LucenePathSearcher searcher;
    //dependencies.
    private DefaultLuceneIndexAccessor indexAccessor;

    private ProcessorManager manager;

    private List<ProcessorInterface> processorsList;

    @Before
    public void setUp() throws IOException {
        indexAccessor = new DefaultLuceneIndexAccessor(searcher);
        MockitoAnnotations.initMocks(indexAccessor);
        manager = new ProcessorManager(indexAccessor);
        when(searcher.getIndexPath()).thenReturn(Paths.get("target","test-index"));
        processorsList = new ArrayList<>();
        processorsList.add(new ProcessorInterface() {
            @Override
            public void execute(Commit commitInfo) {
                log.info("inside processor execute, processing: " + commitInfo);
            }
        });
//        manager.setReferenceToPackagePath("pl.hycom.jira.plugins.gitlab.integration.gitpanel");
    }

    @Test
    public void startProcessorsTest() {
        assertThat("Tested List is not empty", processorsList, is(notNullValue()));
        assertThat("Reference should be injected.", manager, is(notNullValue()));
        List<Commit> list = new ArrayList<>();
        //list.add(new CommitEvent());
        manager.startProcessors(list);
    }

    @Test(expected = NullPointerException.class)
    public void startProcessorsThrowsNPETest() {
        manager.startProcessors(null);
    }

}