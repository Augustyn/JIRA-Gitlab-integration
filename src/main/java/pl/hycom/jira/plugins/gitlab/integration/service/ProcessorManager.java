package pl.hycom.jira.plugins.gitlab.integration.service;

/*
 * <p>Copyright (c) 2016, Authors
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
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Service;
import pl.hycom.jira.plugins.gitlab.integration.exceptions.ProcessException;
import pl.hycom.jira.plugins.gitlab.integration.model.CommitData;
import pl.hycom.jira.plugins.gitlab.integration.service.processors.ProcessorInterface;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karol Joachimiak on 08.04.2016.
 */
@Service
@Log4j
public class ProcessorManager {
    @Setter
    @NotNull
    private List<ProcessorInterface> processorsList = new ArrayList<>();

    public void startProcessors(List<CommitData> commitInfoList) {
        log.info("Processor manager started. Tasks to process: "+ processorsList.size());
        long startTime = System.currentTimeMillis();
        for (CommitData commitInfo : commitInfoList) {
            for (ProcessorInterface processor : processorsList) {
                ProcessorInterface interf = processor;
                try {
                    interf.execute(commitInfo);
                } catch (ProcessException e) {
                    log.info("Processor throws exception but it was ignored");
                }
            }
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Method startProcessors in class " + ProcessorManager.class.getName()+" took " +elapsedTime+" ms to execute." );
    }

}
