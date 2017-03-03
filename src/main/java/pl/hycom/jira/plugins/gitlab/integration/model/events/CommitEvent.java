/*
 *  Copyright 2016-2017 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package pl.hycom.jira.plugins.gitlab.integration.model.events;


import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
@Data
public class CommitEvent {

    @JsonProperty("id")
    public String id;
    @JsonProperty("message")
    public String message;
    @JsonProperty("timestamp")
    public String timestamp;
    @JsonProperty("url")
    public String url;
    @JsonProperty("author")
    public Author author;
    @JsonProperty("added")
    public List<String> added = new ArrayList<String>();
    @JsonProperty("modified")
    public List<String> modified = new ArrayList<String>();
    @JsonProperty("removed")
    public List<Object> removed = new ArrayList<Object>();

}