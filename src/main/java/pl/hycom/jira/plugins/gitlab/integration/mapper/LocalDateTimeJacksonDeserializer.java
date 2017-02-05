/*
 * Copyright 2012-2015 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.hycom.jira.plugins.gitlab.integration.mapper;

import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import javax.annotation.Nullable;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Simple LocalDateTime deserializer from ISO pattern
 * @see DateTimeFormatter#ISO_ZONED_DATE_TIME
 * Will return null, if date is not in appropriate format.
 */
@Log4j
public class LocalDateTimeJacksonDeserializer extends JsonDeserializer<LocalDateTime>{
    @Override @Nullable
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        LocalDateTime result = null;
        try {
            final DateTimeFormatter date = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            result = LocalDateTime.parse(jp.getText(), date);
        } catch (DateTimeParseException e) {
            log.info("Failed to parse date in format: " + jp.getText() + " as LocalDateTime object, using pattern: " +
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME.toString() + ". Returning null date instead(!)");
        }
        return result;
    }
}
