/*
 *
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package pl.hycom.jira.plugins.gitlab.integration.util;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import pl.hycom.jira.plugins.gitlab.integration.mapper.LocalDateTimeJacksonSerializer;

/**
 * Factory for creating Jackson Object Mapper with custom serializer and deserializers.
 */
public class JacksonMapperFactory {

    /*public ObjectMapper getObjectMapperInstance() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule jiraModule = new SimpleModule("jiraModule", new Version(1, 0, 0, null));
        jiraModule.addSerializer(new LocalDateTimeJacksonSerializer());


        mapper.registerModule(jiraModule);
        return mapper;
    }*/
}
