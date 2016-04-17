package pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl;

import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by anon on 17.04.16.
 */
public class GitLabManager {

    private String urlMock;
    private String privateTokenMock;
    private List<CommitData> commitInfoData;

    public void handleEvent(){
        //TO-DO
        runProcessors();
    }

    public List<CommitData> parseCommitData(){
        //TO-DO
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlMock, String.class);


        return null;
    }

    public void runProcessors(){
       //TO-DO
        parseCommitData();

    }

}
