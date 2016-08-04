package pl.hycom.jira.plugins.gitlab.integration.interceptor;

import lombok.extern.log4j.Log4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible for logging REST messages. It is used by Spring Rest Template.
 *
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
 *
 */
@Component
@Log4j
public class RestLoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final Pattern pattern = Pattern.compile("\"password\":\"(.*)\"");
    private static final String ENCODING = "UTF-8";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }
    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        /*if (!log.isDebugEnabled()) {
            return;
        }*/
        HttpHeaders headers = request.getHeaders();
        StringBuilder sb = new StringBuilder();
        sb.append("Thread: ").append(Thread.currentThread().getName()).append("\n")
                .append("===/home/higashi/howtos/git/git.fixing.repo:git==================== request begin ========================================").append("\n")
                .append("URI : ").append(request.getURI()).append("\n").append("Method : ").append(request.getMethod()).append("\n")
                .append("Headers:").append("\n");
        for (Map.Entry<String, List<String>> header : headers.entrySet()) {
            sb.append("key: ").append(header.getKey()).append(", value: ");
            sb.append((header.getValue()) != null ? header.getValue().toString() : "null");
            sb.append("\n");
        }
        String bodyStr = new String(body, ENCODING);
        Matcher m = pattern.matcher(bodyStr);
        if (m.find()) {
            bodyStr = m.replaceAll("password\": \"*****\" ");
        }
        sb.append("Request Body : ").append(bodyStr).append("\n");
        sb.append("======================= request ends =========================================");
        log.info(sb.toString());
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        /*if (!log.isDebugEnabled()) {
            return;
        }*/
        StringBuilder sb = new StringBuilder();
        sb.append("Thread: ").append(Thread.currentThread().getName()).append("\n")
                .append("======================= response begin =======================================")
                .append("\n")
                .append("status code: ").append(response.getStatusCode()).append("\n")
                .append("status text: ").append(response.getStatusText()).append("\n")
                .append("headers: \n");
        HttpHeaders headers = response.getHeaders();
        for (Map.Entry<String, List<String>> header : headers.entrySet()) {
            sb.append("key: ").append(header.getKey()).append(", value: ");
            sb.append((header.getValue()) != null ? header.getValue().toString() : "null");
            sb.append("\n");
        }
        String body = ("" + IOUtils.toString(response.getBody())); //casting is needed when response is null or Exception.
        sb.append("Response Body : ").append(body).append("\n");
        sb.append("======================= response end =========================================");
        log.info(sb.toString());
    }
}
