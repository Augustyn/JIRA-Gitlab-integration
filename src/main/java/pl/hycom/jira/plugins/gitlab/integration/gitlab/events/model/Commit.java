package pl.hycom.jira.plugins.gitlab.integration.gitlab.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

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
@Generated("org.jsonschema2pojo")
@Data
public class Commit {

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