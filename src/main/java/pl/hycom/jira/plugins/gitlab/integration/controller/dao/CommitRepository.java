package pl.hycom.jira.plugins.gitlab.integration.controller.dao;

import lombok.extern.log4j.Log4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl.Commit;
import pl.hycom.jira.plugins.gitlab.integration.controller.util.TemplateFactory;

import java.util.List;

/**
 * Created by Kamil Rogowski on 22.04.2016.
 */
@Log4j
@Repository
public class CommitRepository implements ICommitDao {

    private String urlMock = "https://gitlab.com/api/v3/projects/1063546/repository/commits";
    private String privateTokenMock = "KCi3MfkU7qNGJCe3pQUW";

    private TemplateFactory templateFactory = new TemplateFactory();
    private RestTemplate restTemplate = templateFactory.restTemplate;
    private HttpHeaders headers = templateFactory.httpHeaders;


    @Override
    public List<Commit> getNewCommits(int perPage, int pageNumber) {
        String urlMockWithPageNumber = urlMock + "?per_page=" + Integer.toString(perPage) + "&page=" + Integer.toString(pageNumber);
        headers.set("PRIVATE-TOKEN", privateTokenMock);
        HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<List<Commit>> response = restTemplate.exchange(urlMockWithPageNumber, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<List<Commit>>() {
                });

        return response.getBody();
    }

    @Override
    public Commit getOneCommit(String shaSum) {
        String oneCommitUrlMock = urlMock + "/" + shaSum;
        headers.set("PRIVATE-TOKEN", privateTokenMock);
        HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Commit> response = restTemplate.exchange(oneCommitUrlMock, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<Commit>() {
                });

        return response.getBody();
    }
}
