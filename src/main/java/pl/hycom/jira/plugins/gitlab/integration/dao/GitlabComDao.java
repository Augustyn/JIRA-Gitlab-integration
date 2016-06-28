package pl.hycom.jira.plugins.gitlab.integration.dao;
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

import lombok.extern.log4j.Log4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import pl.hycom.jira.plugins.gitlab.integration.model.GitlabProject;
import pl.hycom.jira.plugins.gitlab.integration.util.TemplateFactory;

import java.util.List;


@Log4j
@Repository
public class GitlabComDao implements IGitlabComDao {

    private static final String PROJECT = "/api/v3/projects/owned";

    @Override
    public List<GitlabProject> getGitlabProjects(ConfigEntity configEntity){
        HttpEntity<?> requestEntity = new HttpEntity<>(new TemplateFactory().getHttpHeaders().setAuth(configEntity.getClientId()).build());
        ResponseEntity<List<GitlabProject>> response = new TemplateFactory().getRestTemplate().exchange(configEntity.getLink() + PROJECT, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<List<GitlabProject>>() {
                });
        return response.getBody();
    }
}
