package pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl;

/*
 * <p>Copyright (c) 2016, Damian Deska, Kamil Rogowski
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

import lombok.extern.log4j.Log4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by anon on 17.04.16.
 */
@Component
@Log4j
public class GitLabManager {

    private String urlMock = "https://gitlab.com/api/v3/projects/1063546/repository/commits";
    private String privateTokenMock = "KCi3MfkU7qNGJCe3pQUW";

    public GitLabManager() {

    }

    public void handleEvent() {
        //TODO
        runProcessors();
    }

    public List<CommitData> parseCommitData() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("PRIVATE-TOKEN", privateTokenMock);
        HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);

        ResponseEntity<List<Commit>> response = restTemplate.exchange(urlMock, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<List<Commit>>() {

                });
        List<Commit> commits = response.getBody();

        log.info("Last commit id:" + commits.get(0).getId());

        return null;

    }

    public void runProcessors() {
        //TODO
        parseCommitData();
    }

}
