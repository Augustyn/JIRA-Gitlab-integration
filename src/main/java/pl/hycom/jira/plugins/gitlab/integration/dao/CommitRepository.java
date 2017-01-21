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
package pl.hycom.jira.plugins.gitlab.integration.dao;

import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.util.HttpHeadersBuilder;
import pl.hycom.jira.plugins.gitlab.integration.util.TemplateFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j
@Repository
public class CommitRepository implements ICommitDao {

    private static final String COMMIT_BASE = "/api/v3/projects/{gitlabprojectid}/repository/commits";
    private static final String COMMIT_URL =  COMMIT_BASE + "?per_page={perPage}&page={pageNumber}";
    private static final String COMMIT_SINGLE_URL = COMMIT_BASE + "/{sha1sum}";

    @Autowired private TemplateFactory restTemplate;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<Commit> getNewCommits(ConfigEntity configEntity, int perPage, int pageNumber) throws IOException {
        log.info("Trying to reach url: {}, with projectId: {}");
        HttpEntity<?> requestEntity = new HttpEntity<>(HttpHeadersBuilder.getInstance().setAuth(configEntity.getGitlabSecretToken()).get());
        ResponseEntity<String> response = restTemplate.getRestTemplate().exchange(configEntity.getGitlabURL() + COMMIT_URL, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<String>() {
                }, configEntity.getGitlabProjectId(), Integer.toString(perPage), Integer.toString(pageNumber));
        log.debug("Received response: " + response.getBody());
        List<Commit> commitList = mapper.readValue(response.getBody(), mapper.getTypeFactory().constructCollectionType(List.class, Commit.class));
        log.debug(("Received response as commit list: " + commitList));
        return commitList;
    }

    @Override
    public Commit getOneCommit(ConfigEntity config, String shaSum) throws IOException {
        log.info("Getting one commit from git repository: " + config.getGitlabURL() + " with secret: " + config.getGitlabSecretToken() + " and project Id: " + config.getGitlabProjectId());
        HttpEntity<?> requestEntity = new HttpEntity<>(HttpHeadersBuilder.getInstance().setAuth(config.getGitlabSecretToken()).get());
        ResponseEntity<String> response = restTemplate.getRestTemplate().exchange(config.getGitlabURL() + COMMIT_SINGLE_URL, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<String>() {
                }, config.getGitlabProjectId(), shaSum);
        if (response != null) {
            log.debug("Received response: " + response.getBody());
            Commit commit = mapper.readValue(response.getBody(), Commit.class);
            log.debug("Resolved response as commit: " + commit);
            return commit;
        }
        return null;
    }

    public void setRestTemplate(TemplateFactory restTemplate) {
        this.restTemplate = restTemplate;
    }
}
