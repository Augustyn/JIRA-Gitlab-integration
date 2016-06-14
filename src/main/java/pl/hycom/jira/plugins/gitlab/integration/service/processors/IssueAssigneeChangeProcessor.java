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

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
import pl.hycom.jira.plugins.gitlab.integration.util.TemplateFactory;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Damian Deska on 6/14/16.
 */
@Log4j
public class IssueAssigneeChangeProcessor {

    public void changeIssueAssignee(String jiraUrl, String issueKey) {
        jiraUrl += "jira/rest/api/2/issue/" + issueKey;
//        //HttpEntity<?> requestEntity = new HttpEntity<>()
//        ResponseEntity<Commit> response = new TemplateFactory().getRestTemplate().exchange(jiraUrl, HttpMethod.PUT,
//                new ParameterizedTypeReference<Commit>() {
//                },repositoryUrl,shaSum);
        try {
            URI uri = new URI("the server address goes here");
            URL url = new URL(jiraUrl);
            log.info(url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Gson gson = new Gson();
            String x = "{\"user\": \"lololo\"}";
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.addRequestProperty("Content-Type", "application/json");
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(gson.toJson(x));
            log.info(gson.toJson(x));
            out.close();
        } catch (Exception e) {

        }
    }

}
