package ut.pl.hycom.jira.plugins.gitlab.integration.service;

import lombok.extern.log4j.Log4j;
import org.junit.Ignore;
import org.junit.Test;
import pl.hycom.jira.plugins.gitlab.integration.service.processors.IssueStatusChangeProcessor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

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
public class IssueStatusChangeProcessorTest {

    @Test
    @Ignore
    public void getPossibleStatuesTest() throws URISyntaxException, IOException {
        IssueStatusChangeProcessor statusChanger = new IssueStatusChangeProcessor();
        List<String> result = statusChanger.getPossibleIssueStatuses();
        for(String statuses : result) {
            log.info(statuses);
        }
    }

    @Test
    @Ignore
    public void changeIssueStatusTest(){
        IssueStatusChangeProcessor statusChanger = new IssueStatusChangeProcessor();
        statusChanger.changeIssueStatus();
    }
}
