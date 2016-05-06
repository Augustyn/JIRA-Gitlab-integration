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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamil Rogowski on 22.04.2016.
 */
@Log4j
@Repository
public class CommitRepository implements ICommitDao {

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    private String urlMock = "https://gitlab.com/api/v3/projects/1063546/repository/commits";
    private String privateTokenMock = "KCi3MfkU7qNGJCe3pQUW";

    @Override
    public List<Commit> getNewCommits(int perPage) {
        int pageNumber = 1;
        String urlMockWithPageNumber;
        List<Commit> newCommitsList = new ArrayList<>();

        urlMock += "?per_page=" + Integer.toString(perPage) + "&page=";
        headers.set("PRIVATE-TOKEN", privateTokenMock);
        HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);

        //TODO zapisywanie do Lucyny (PIP-32)
        addingCommitsToList:
        while(true) {
            urlMockWithPageNumber = urlMock + Integer.toString(pageNumber);
            log.info(urlMockWithPageNumber);
            ResponseEntity<List<Commit>> response = restTemplate.exchange(urlMockWithPageNumber, HttpMethod.GET, requestEntity,
                    new ParameterizedTypeReference<List<Commit>>() {
                    });

            for(Commit commit : response.getBody() ) {
                //TODO sprawdzenie czy commit jest juz zaindeksowany
                if( pageNumber < 3) {
                    newCommitsList.add(commit);
                } else {
                    break addingCommitsToList;
                }
            }

            pageNumber++;
        }

       return newCommitsList;
    }

    @Override
    public Commit getOneCommit(String shaSum) {
        urlMock += "/" + shaSum;
        headers.set("PRIVATE-TOKEN", privateTokenMock);
        HttpEntity<?> requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Commit> response = restTemplate.exchange(urlMock, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<Commit>() {
                });

        return response.getBody();
    }
}
