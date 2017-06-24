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
package pl.hycom.jira.plugins.gitlab.integration.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.lucene.index.IndexWriter;
import org.springframework.stereotype.Service;
import pl.hycom.jira.plugins.gitlab.integration.exceptions.ProcessException;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.search.LuceneIndexAccessor;
import pl.hycom.jira.plugins.gitlab.integration.service.processors.ProcessorInterface;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Inject)) //Inject all final variables.
public class ProcessorManager {
    private final LuceneIndexAccessor accessor;
    @Setter @Getter
    private List<ProcessorInterface> processorsList = new ArrayList<>();

    public void startProcessors(@NotNull List<Commit> commitInfoList) {
        log.info("Processor manager started. Processors to process: " +
                processorsList.size()+". Commits to process: " + commitInfoList.size());

        long startTime = System.currentTimeMillis();

        for (Commit commitInfo : commitInfoList){
            for (ProcessorInterface processor : processorsList) {
                ProcessorInterface interf = processor;
                try {
                    interf.execute(commitInfo);
                } catch (ProcessException e) {
                    log.error("Processor throws exception but it was ignored: " + e.getMessage());
                }
            }
        }
        try {
            final IndexWriter writer = accessor.getIndexWriter();
            if (writer != null) {
                writer.commit();
            }
        } catch (IOException e) {
            log.info("Cannot obtain indexWriter. Skipping commit. Enable debug for more info");
            log.debug("Failed to obtain index writer with message: " + e.getMessage(), e);
        }
        log.info("Method startProcessors in class " + ProcessorManager.class.getName() + " took " +
                (System.currentTimeMillis() - startTime) + " ms to execute.");
    }

}
