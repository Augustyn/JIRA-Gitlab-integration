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
package pl.hycom.jira.plugins.gitlab.integration.validation;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents an error in configuration of specific field
 */
public class ValidationError {

    private String field;

    private String error;

    private Collection<String> params = new ArrayList<>();

    /**
     * @param field  UserPref name to which the error refers to
     * @param error  i18n key of the error message to be displayed
     * @param params optional parameters
     */
    public ValidationError(String field, String error, Collection<String> params) {
        this.field = field;
        this.error = error;
        this.params = params;
    }

    /**
     * @param field UserPref name to which the error refers to
     * @param error i18n key of the error message to be displayed
     */
    public ValidationError(String field, String error) {
        this.field = field;
        this.error = error;
    }
}