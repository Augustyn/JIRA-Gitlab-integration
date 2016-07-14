package pl.hycom.jira.plugins.gitlab.integration.util;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
/**
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
@Service
public class TemplateFactory {

    HttpHeaders headers = null;

    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate;
    }

    public TemplateFactory getHttpHeaders() {
        this.headers = new HttpHeaders();

        return this;
    }

    public TemplateFactory setAuth(String privateToken) {
        this.headers.set("PRIVATE-TOKEN", privateToken);
        return this;
    }

    public HttpHeaders build() {
        return this.headers;
    }

}
