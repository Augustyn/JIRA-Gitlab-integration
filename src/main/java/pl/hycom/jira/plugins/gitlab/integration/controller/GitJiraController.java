package pl.hycom.jira.plugins.gitlab.integration.controller;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.ofbiz.OfBizDelegator;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.sal.api.message.I18nResolver;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.hycom.jira.plugins.gitlab.integration.gitlab.events.Push;
import pl.hycom.jira.plugins.gitlab.integration.service.CommitManager;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
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

/**
 * Controller used for refreshing project, based on notifications hooks from GitLab.
 *
 */
@Path("gitlab")
@Controller
@Log4j
public class GitJiraController {

    @Autowired
    private I18nResolver i18nResolver;
    @Autowired
    private JiraAuthenticationContext authenticationContext;
    @Autowired private CommitManager manager;
    //4spring dep.injection
    public GitJiraController() {
    }


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
        } catch (SQLException | ParseException | IOException e) {
            log.error("Error refreshing commits from Gitlab, with exception: ", e);
        }
    }

}



