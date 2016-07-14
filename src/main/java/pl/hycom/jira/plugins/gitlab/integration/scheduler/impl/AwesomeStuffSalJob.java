package pl.hycom.jira.plugins.gitlab.integration.scheduler.impl;

import com.atlassian.sal.api.scheduling.PluginJob;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeException;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuff;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuffDao;

import java.util.Map;

import static com.atlassian.jira.util.dbc.Assertions.notNull;
@Log4j
@Component

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
 */
public class AwesomeStuffSalJob implements PluginJob {

    public void execute(Map<String, Object> jobDataMap)
    {
        final AwesomeStuffSalJobsImpl monitor = (AwesomeStuffSalJobsImpl)jobDataMap.get(AwesomeStuffSalJobsImpl.KEY);
        notNull("monitor", monitor);
        AwesomeStuffDao awesomeStuffDao = monitor.getAwesomeStuffDao();
        notNull("awesomeStuffDao", awesomeStuffDao);

        log.info("Checking our awesome stuff...");
        try
        {
            AwesomeStuff[] awesomeStuffs = awesomeStuffDao.findByAll();
            log.info("We've got " + awesomeStuffs.length + " awesome stuff...");
        }
        catch (AwesomeException e)
        {
            log.error("Error retrieving all the awesome stuff: " + e.getMessage(), e);
        }
    }
}
