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
package pl.hycom.jira.plugins.gitlab.integration.service.processors;

import lombok.NoArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.exceptions.ProcessException;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.search.CommitIndex;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@NoArgsConstructor
@Component
@Log4j
public class IssueCommitIndexerProcessor implements ProcessorInterface {
    @Autowired private CommitIndex commitIndexer;

    @Override
    public void execute(@NotNull Commit commit) throws ProcessException {
        try {
            commitIndexer.indexFile(commit);
        } catch (IOException e) {
            log.info("Failed to index commit with id: "+commit.getId() + e.getMessage());
        }
    }
}
