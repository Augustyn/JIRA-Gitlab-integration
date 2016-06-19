package pl.hycom.jira.plugins.gitlab.integration.controller;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.ofbiz.OfBizDelegator;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.sal.api.message.I18nResolver;
import com.google.gson.Gson;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

/**
 * Controller used for validations and other useful things
 *
 * @author maciej.lizniewicz
 */
@Path("")//url na ktory wysyłamy zapytanie
@Controller
public class GitJiraController {

    @Autowired
    private I18nResolver i18nResolver;
    @Autowired
    private JiraAuthenticationContext authenticationContext;

    //4spring dep.injection
    public GitJiraController() {
    }


    @Path("/changeme")//zloz to z poprzednim pathem
    @POST
    @Produces({MediaType.TEXT_HTML})
    @Consumes("application/json")
    public Response getRequest(HttpResponse response){//commit
//..powinno sprawdzac requesta i uruchamiac menadżera ktory pobiera commityA
    //json zamieni response z gitlaba w postaci
    //http://docs.gitlab.com/ce/api/commits.html
    //na obiekt
        Header header = response.getFirstHeader("X-Gitlab-Event");

        if(header == null) {
            return null;
        }

        if("Note Hook".equals(header.getValue())) {
            //manager
        };


        try {
            InputStream content = response.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok(response.getEntity(),MediaType.TEXT_HTML).build();
    }

}



