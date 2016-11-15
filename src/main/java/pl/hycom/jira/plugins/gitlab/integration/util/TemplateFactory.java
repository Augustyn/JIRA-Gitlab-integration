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

import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.hycom.jira.plugins.gitlab.integration.interceptor.RestLoggingInterceptor;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class TemplateFactory {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private RestLoggingInterceptor interceptor = new RestLoggingInterceptor();
    private static final List<HttpMessageConverter<?>> formConverters = new LinkedList<>();
    static {
        formConverters.add(new org.springframework.http.converter.ByteArrayHttpMessageConverter());
        formConverters.add(new org.springframework.http.converter.StringHttpMessageConverter(UTF8));
        formConverters.add(new org.springframework.http.converter.ResourceHttpMessageConverter());
        formConverters.add(new org.springframework.http.converter.xml.SourceHttpMessageConverter());
        formConverters.add(new org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter());
        formConverters.add(new org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter());
        formConverters.add(new org.springframework.http.converter.json.MappingJackson2HttpMessageConverter());
    }
    public RestTemplate getRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(interceptor));
        //Needed by interceptor, to be able to read response body at lease twice. Once to log it, second - to get response object:
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        restTemplate.setMessageConverters(formConverters);
        //assert(restTemplate.getMessageConverters().contains(new org.springframework.http.converter.json.MappingJackson2HttpMessageConverter()));
        return restTemplate;
    }
}
