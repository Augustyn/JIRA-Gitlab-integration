/*
 * Controller used for refreshing project, based on notifications hooks from GitLab.
 *
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
package pl.hycom.jira.plugins.gitlab.integration.controller;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.sal.api.message.I18nResolver;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.hycom.jira.plugins.gitlab.integration.gitlab.events.Push;
import pl.hycom.jira.plugins.gitlab.integration.service.CommitManager;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.SQLException;

@Path("gitlab")
@Controller
@Log4j
@NoArgsConstructor
public class GitJiraController {

    @Autowired private I18nResolver i18nResolver;
    @Autowired private JiraAuthenticationContext authenticationContext;
    @Autowired private CommitManager manager;

    @Path("/listener")//zloz to z poprzednim pathem
    @POST
    @Produces({MediaType.TEXT_HTML})
    @Consumes("application/json")
    public void getRequest(HttpServletRequest request, Push event){
        String header = request.getHeader("X-Gitlab-Event");
        if(header == null || !header.contains("Hook")) {
            log.debug("Received request is not Gitlab request. Skipping processing");
            return;
        }
        try {
            event.getProject().getName(); //FIXME: get JIRA project Id and call: updateCommitsForProject(id);
            manager.updateCommitsForAll();
        } catch (SQLException | IOException e) {
            log.error("Error refreshing commits from Gitlab, with exception: ", e);
        }
    }

}



