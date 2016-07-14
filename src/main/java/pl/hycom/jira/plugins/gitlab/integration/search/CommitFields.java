package pl.hycom.jira.plugins.gitlab.integration.search;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public enum CommitFields {
    ID,
    SHORT_ID,
    TITLE,
    AUTHOR_NAME,
    AUTHOR_EMAIL,
    CREATED,
    COMMIT_MESSAGE,
    GIT_PROJECT_ID,
    JIRA_ISSUE_KEY;
    public static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.sssZ");
}
