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


package pl.hycom.jira.plugins.gitlab.integration.service;


import pl.hycom.jira.plugins.gitlab.integration.model.FormField;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.rest.ErrorCollection;
import pl.hycom.jira.plugins.gitlab.integration.rest.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;


/**
 * Simple implementation of Validator.
 */
@Component
public class ValidatorImpl implements Validator {

    private static final Logger log = LoggerFactory.getLogger(ValidatorImpl.class);

    @Override
    public ErrorCollection validate(Map<FormField, String> paramMap){
        ErrorCollection errorCollection = new ErrorCollection();


        for (Map.Entry<FormField, String> entry : paramMap.entrySet()) {
            entry.getKey().validate(errorCollection, entry.getValue());
            if(FormField.CLIENTID.equals(entry.getKey()) && !FormField.clientIdPattern.matcher(entry.getValue()).matches()){
                log.error("ClientId uses wrong characters. Use aA-zZ, 0-9.");
                errorCollection.addValidationError(new ValidationError(FormField.CLIENTID.getFieldName(),
                        "jirasectionaction.errors.ClientIdError"));
            }
            if(FormField.CLIENTSECRET.equals(entry.getKey()) && !FormField.clientSecretPattern.matcher(entry.getValue()).matches()){
                log.error("ClientSecret uses wrong characters. Use aA-zZ, 0-9.");
                errorCollection.addValidationError(new ValidationError(FormField.CLIENTSECRET.getFieldName(),
                        "jirasectionaction.errors.ClientSecretError"));
            }
            if(FormField.PROJECTID.equals(entry.getKey()) && !FormField.projectIdPattern.matcher(entry.getValue()).matches()){
                log.error("ProjectId uses wrong characters. Use aA-zZ, 0-9.");
                errorCollection.addValidationError(new ValidationError(FormField.PROJECTID.getFieldName(),
                        "jirasectionaction.errors.ProjectIdError"));
            }
            if(FormField.GITLABLINK.equals(entry.getKey()) && !FormField.gitlabLinkPattern.matcher(entry.getValue()).matches()){
                log.error("GitlabLink uses wrong characters.");
                errorCollection.addValidationError(new ValidationError(FormField.GITLABLINK.getFieldName(),
                        "jirasectionaction.errors.GitlabLinkError"));
            }
        }
        return errorCollection;
    }


    @Override
    public boolean checkIfClientId(String value) {
        return FormField.clientIdPattern.matcher(value).matches();
    }
    @Override
    public boolean checkIfClientSecret(String value) {
        return FormField.clientSecretPattern.matcher(value).matches();
    }
    @Override
    public boolean checkIfGitlabLink(String value) {
        return FormField.gitlabLinkPattern.matcher(value).matches();
    }

    @Override
    public boolean checkIfProjectId(String value) {
        return FormField.projectIdPattern.matcher(value).matches();
    }
}