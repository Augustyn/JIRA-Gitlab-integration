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
package pl.hycom.jira.plugins.gitlab.integration.util;

import org.springframework.http.HttpHeaders;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * Builder for HttpHeaders, used by Spring Template
 */
public class HttpHeadersBuilder {
    private final HttpHeaders httpHeaders;

    private HttpHeadersBuilder() {
        this.httpHeaders = new HttpHeaders();
        this.httpHeaders.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
    }

    public static HttpHeadersBuilder getInstance() {
        return new HttpHeadersBuilder();
    }
    /**
     * Sets http header 'Private-Token'
     * @param auth token value
     */
    public HttpHeadersBuilder setAuth(String auth) {
        this.httpHeaders.set("PRIVATE-TOKEN", auth);
        return this;
    }
    /**
     * Sets http header 'Private-Token'
     * Convinence method.
     * @see  HttpHeadersBuilder#setAuth
     * @param auth token value
     */
    public HttpHeadersBuilder setPrivateToken(String auth) {
        return this.setAuth(auth);
    }

    public HttpHeaders get() {
        return this.httpHeaders;
    }
}
