package pl.hycom.jira.plugins.gitlab.integration.service.processors;

/*
 * <p>Copyright (c) 2016, Damian Deska
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.helper.HttpConnection;


import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Damian Deska on 6/17/16.
 */
@Log4j
public class IssueStatusChangeProcessor {
    //TODO zamiana parametrow z wpisanych na sztywno na pobrane z konfiguracji pluginu
    HttpClient client = HttpClientBuilder.create().build();

    public void changeIssueStatus() {
        String id = "41";
        try {
            HttpPost request = new HttpPost("http://vagrant:2990/jira/rest/api/2/issue/PIP-1/transitions\"");
            StringEntity params =new StringEntity("{\"transitions\":{\"id\":\"" + id + "\"}}");
            request.addHeader("content-type", "application/x-www-form-urlencoded");
            request.setEntity(params);
            HttpResponse response = client.execute(request);

            // handle response here...
        }catch (Exception ex) {
            // handle exception here
        }
    }

    public List<String> getPossibleIssueStatuses() throws URISyntaxException, IOException {

        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("vagrant:2990")
                .setPath("/jira/rest/api/2/issue/PIP-1/transitions")
                .build();
        HttpGet httpget = new HttpGet(uri);
        httpget.addHeader("Authorization", "Basic YWRtaW46YWRtaW4=");

        HttpResponse response = client.execute(httpget);

        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        List<String> result = new ArrayList<String>();
        while ((line = rd.readLine()) != null) {
            int endIndex;
            for (int index = line.indexOf("name"); index >= 0; index = line.indexOf("name", index + 1))
            {
                endIndex = line.indexOf("\"", index + 7);
                String status = line.substring(index + 7, endIndex);
                if(!result.stream().anyMatch(str -> str.trim().equals(status))) {
                    result.add(status);
                }
            }
        }
        return result;
    }

}
