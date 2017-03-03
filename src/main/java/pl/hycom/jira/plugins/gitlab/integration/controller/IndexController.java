/*
 *  Copyright 2016-2017 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package pl.hycom.jira.plugins.gitlab.integration.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import pl.hycom.jira.plugins.gitlab.integration.search.CommitIndex;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Simple controller for testing
 * endpoint http://localhost:2990/jira/rest/jira-gitlab/1.0/index/clear
 */
@Log4j
@Path("/index")
@Controller
@Produces({MediaType.APPLICATION_JSON})
@RequiredArgsConstructor(onConstructor = @__(@Inject)) //Inject all final variables.
public class IndexController {

    private final CommitIndex commitIndex;

    @Path("/clear")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response doValidation() {
        try {
            commitIndex.clearIndex();
        } catch (IOException e) {
            log.info("Failed to clear index, with message: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        log.info("Validation: Everything Went okay, returning OK.");
        return Response.ok().build();
    }
}
