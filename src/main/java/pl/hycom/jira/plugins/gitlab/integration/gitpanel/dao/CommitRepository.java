package pl.hycom.jira.plugins.gitlab.integration.gitpanel.dao;

import lombok.extern.log4j.Log4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl.Commit;

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
    public List<Commit> getAllCommits() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("PRIVATE-TOKEN", privateTokenMock);
        HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<List<Commit>> response = restTemplate.exchange(urlMock, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<List<Commit>>() {
                });

       return response.getBody();
    }

    @Override
    public Commit getOneCommit() {
        //TODO
        return null;
    }
}
