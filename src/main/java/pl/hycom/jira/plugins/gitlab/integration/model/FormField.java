/*
 * <p>Copyright (c) 2016, Authors
 * Project:  gitlab-integration.</p>
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

package pl.hycom.jira.plugins.gitlab.integration.model;


import pl.hycom.jira.plugins.gitlab.integration.validation.ErrorCollection;
import pl.hycom.jira.plugins.gitlab.integration.validation.ValidationError;

import java.util.regex.Pattern;

public enum FormField {

    PROJECTID("projectId") {
        @Override
        public void validate(ErrorCollection errorCollection, String value) {
            if (value == null || value.isEmpty()) {
                errorCollection.addValidationError(new ValidationError(FormField.PROJECTID.fieldName,
                        ERROR_PREFIX + EMPTY_FIELD));
            } else if (!projectIdPattern.matcher(value).matches()) {
                errorCollection.addValidationError(new ValidationError(FormField.PROJECTID.fieldName,
                        ERROR_PREFIX + "invalidValue"));
            }
        }
    },
    CLIENTID("clientId") {
        @Override
        public void validate(ErrorCollection errorCollection, String value) {
            if (value == null || value.isEmpty()) {
                errorCollection.addValidationError(new ValidationError(FormField.CLIENTID.fieldName,
                        ERROR_PREFIX + EMPTY_FIELD));
            } else if (!clientIdPattern.matcher(value).matches()) {
                errorCollection.addValidationError(new ValidationError(FormField.CLIENTID.fieldName,
                        ERROR_PREFIX + "invalidValue"));
            }
        }
    },
    CLIENTSECRET("clientSecret") {
        @Override
        public void validate(ErrorCollection errorCollection, String value) {
            if (value == null || value.isEmpty()) {
                errorCollection.addValidationError(new ValidationError(FormField.CLIENTSECRET.fieldName,
                        ERROR_PREFIX + EMPTY_FIELD));
            } else if (!clientSecretPattern.matcher(value).matches()) {
                errorCollection.addValidationError(new ValidationError(FormField.CLIENTSECRET.fieldName,
                        ERROR_PREFIX + "invalidValue"));
            }
        }
    }, GITLABLINK("gitlabLink") {
        @Override
        public void validate(ErrorCollection errorCollection, String value) {
            if (value == null || value.isEmpty()) {
                errorCollection.addValidationError(new ValidationError(FormField.GITLABLINK.fieldName,
                        ERROR_PREFIX + EMPTY_FIELD));
            } else if (!gitlabLinkPattern.matcher(value).matches()) {
                errorCollection.addValidationError(new ValidationError(FormField.GITLABLINK.fieldName,
                        ERROR_PREFIX + "invalidValue"));
            }
        }
    }
    ,
    GITLABPROJECTNAME("gitlabProjectName") {
        @Override
        public void validate(ErrorCollection errorCollection, String value) {
            if (value == null || value.isEmpty()) {
                errorCollection.addValidationError(new ValidationError(FormField.GITLABPROJECTNAME.fieldName,
                        ERROR_PREFIX + EMPTY_FIELD));
            } else if (!gitlabProjectNamePattern.matcher(value).matches()) {
                errorCollection.addValidationError(new ValidationError(FormField.GITLABPROJECTNAME.fieldName,
                        ERROR_PREFIX + "invalidValue"));
            }
        }
    };

    private final static String ERROR_PREFIX = "jirasectionaction.errors.";
    private final static String EMPTY_FIELD = "emptyField";
    public static final Pattern projectIdPattern = Pattern.compile("([0-9]+)*", Pattern.CASE_INSENSITIVE);
    public static final Pattern clientIdPattern = Pattern.compile("([aA-zZ]*[0-9]*)*", Pattern.CASE_INSENSITIVE);
    public static final Pattern clientSecretPattern = Pattern.compile("([aA-zZ]*[0-9]*)*", Pattern.CASE_INSENSITIVE);
    public static final Pattern gitlabLinkPattern = Pattern.compile("^(https?|ssh)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", Pattern.CASE_INSENSITIVE);
    public static final Pattern gitlabProjectNamePattern = Pattern.compile("([aA-zZ]*[0-9]*)*", Pattern.CASE_INSENSITIVE);

    private final String fieldName;

    private FormField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName(){
        return fieldName;
    }
    /**
     * Checks field value for correctness.
     *
     * @param errorCollection container for detected errors
     * @param value           checked
     */
    public abstract void validate(ErrorCollection errorCollection, String value);
}