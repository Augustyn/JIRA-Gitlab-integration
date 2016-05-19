package pl.hycom.jira.plugins.gitlab.integration.dao;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.util.TemplateFactory;

import java.util.List;

/**
 * Created by Kamil Rogowski on 22.04.2016.
 */
@Log4j
@Repository
public class CommitRepository implements ICommitDao {

    private String urlMock = "https://gitlab.com/api/v3/projects/1063546/repository/commits";
    private String privateTokenMock = "KCi3MfkU7qNGJCe3pQUW";

    @Override
    public List<Commit> getNewCommits(int perPage, int pageNumber) {
        String urlMockWithPageNumber = urlMock + "?per_page=" + Integer.toString(perPage) + "&page=" + Integer.toString(pageNumber);
        HttpEntity<?> requestEntity = new HttpEntity<>(new TemplateFactory().getHttpHeaders().setAuth(privateTokenMock).build());
        ResponseEntity<List<Commit>> response = new TemplateFactory().getRestTemplate().exchange(urlMockWithPageNumber, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<List<Commit>>() {
                });

        return response.getBody();
    }

    @Override
    public Commit getOneCommit(String shaSum) {
        String oneCommitUrlMock = urlMock + "/" + shaSum;
        HttpEntity<?> requestEntity = new HttpEntity<>(new TemplateFactory().getHttpHeaders().setAuth(privateTokenMock).build());
        ResponseEntity<Commit> response = new TemplateFactory().getRestTemplate().exchange(oneCommitUrlMock, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<Commit>() {
                });

        return response.getBody();
    }
}
