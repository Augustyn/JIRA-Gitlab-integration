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

import org.springframework.stereotype.Component;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by anon on 17.04.16.
 */
@Component
public class GitLabManager {

    private String urlMock = "https://gitlab.com/api/v3/projects";
    private String privateTokenMock = "KCi3MfkU7qNGJCe3pQUW";
    private List<CommitData> commitInfoData;

    public GitLabManager() {

    }

    public void handleEvent(){
        //TODO
        runProcessors();
    }

    public String getCommitData() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("PRIVATE-TOKEN", privateTokenMock);

        HttpEntity entity = new HttpEntity(headers);

        HttpEntity<String> response = restTemplate.exchange(urlMock, HttpMethod.GET, entity, String.class);

        String tmp = response.getBody();
        String tmp2 = "asdasD";
        System.out.println(tmp);

        return tmp2;

    }

    public List<CommitData> parseCommitData(){
        //TODO

        return null;

    }

    public void runProcessors(){
       //TODO
        getCommitData();

    }

}
