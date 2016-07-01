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

package pl.hycom.jira.plugins.gitlab.integration.controller;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import pl.hycom.jira.plugins.gitlab.integration.dao.IGitlabComManDao;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigManagerDao;
import pl.hycom.jira.plugins.gitlab.integration.model.FormField;
import pl.hycom.jira.plugins.gitlab.integration.service.Validator;
import pl.hycom.jira.plugins.gitlab.integration.validation.ErrorCollection;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
@Log4j
public class JiraSectionAction extends JiraWebActionSupport {


    private static final String ERROR_INVALID_CLIENTID = "jirasection.action.error.invalid.cliendid";
    private static final String ERROR_INVALID_CLIENTSECRET = "jirasection.action.error.invalid.cliendsecret";
    private static final String ERROR_INVALID_PROJECTID = "jirasection.action.error.invalid.projectid";
    private static final String ERROR_INVALID_GITLABLINK = "jirasection.action.error.invalid.gitlablink";
    private static final String ERROR_INVALID_GITLABPROJECTNAME = "jirasection.action.error.invalid.gitlabprojectname";

    @Autowired private Validator validator;
    @Autowired private ConfigManagerDao myConfManager;
    @Autowired private IGitlabComManDao gitlabCommunicationManagerDao;

    private String clientId = "123";
    private String clientSecret = "client123";
    private String gitlabLink = "https://github.com/";
    private String gitProjectId = "123456";
    private String gitlabProjectName = "newname";


    public JiraSectionAction() {
    }

    private ErrorCollection doInternalValidate() {
        Map<FormField, String> paramMap = new HashMap<>();

        String sClientId = getClientId();
        String sClientSecret = getClientSecret();
        String sGitlabLink = getGitlabLink();
        String sProjectId = getGitProjectId();
        String sGitlabProjectNamePattern = getGitlabProjectName();

        paramMap.put(FormField.CLIENTID, sClientId);
        paramMap.put(FormField.CLIENTSECRET, sClientSecret);
        paramMap.put(FormField.GITLABLINK, sGitlabLink);
        paramMap.put(FormField.PROJECTID, sProjectId);
        paramMap.put(FormField.GITLABPROJECTNAME, sGitlabProjectNamePattern);

        final ErrorCollection validate = validator.validate(paramMap);
        validate.getErrorMessages().stream().forEach(e->this.getErrorMessages().add(this.getI18nHelper().getText(e)));
        return validate;
    }

    protected void doValidation() {
        ErrorCollection errorCollection  = doInternalValidate();
        log.warn("Entering doValidation");
        for (Enumeration e =   getHttpRequest().getParameterNames(); e.hasMoreElements() ;) {
            String n = (String)e.nextElement();
            String[] vals =  getHttpRequest().getParameterValues(n);
            log.debug("name " + n + ": " + vals[0]);
        }

        String sClientId = getClientId();
        String sClientSecret = getClientSecret();
        String sGitlabLink = getGitlabLink();
        String sGitlabProjectName = getGitlabProjectName();
        log.debug("The local variable client_id  is currently set to: " + sClientId);
        log.debug("The local variable client_secret is currently set to: " + sClientSecret);
        log.debug("The local variable gitlablink is currently set to: " + sGitlabLink);
        log.debug("The local variable gitlabProjectName is currently set to: " + sGitlabProjectName);


        if (invalidInput()) {
            for (String msg : getErrorMessages()) {
                log.debug("Error message during validation: " + msg);
            }

            for (Map.Entry<String, String> stringStringEntry : getErrors().entrySet()) {
                Map.Entry entry = (Map.Entry) stringStringEntry;
                log.debug("Error during validation: field=" + entry.getKey() + ", error=" + entry.getValue());
            }
        }
    }

    protected String doExecute() throws Exception {
        log.fatal("DEBUG: Entering doExecute");
        for (Enumeration e =   getHttpRequest().getParameterNames(); e.hasMoreElements() ;) {
            String n = (String)e.nextElement();
            String[] vals =  getHttpRequest().getParameterValues(n);
            log.warn("name " + n + ": " + vals[0]);
        }
        if (false) {
            throw new Exception("doExecute raised this exception for some reason");
        }
        return SUCCESS;
    }


    public String doDefault() throws Exception {
        log.debug("Entering doDefault");


        for (Enumeration e =   getHttpRequest().getParameterNames(); e.hasMoreElements() ;) {
            String n = (String)e.nextElement();
            String[] vals =  getHttpRequest().getParameterValues(n);
            log.debug("Parameter " + n + "=" + vals[0]);

        }

        final ErrorCollection errorCollection = doInternalValidate();

        //trying
        myConfManager.updateProjectConfig(Long.valueOf(gitProjectId), gitlabLink, clientSecret, clientId, gitlabProjectName);
        String result = super.doDefault();
        log.debug("Exiting doDefault with a result of: " + result);
        return result;
        //

        /*if (errorCollection.isEmpty() && gitlabCommunicationManagerDao.findGitlabProjectId(gitProjectId)>0){
            myConfManager.updateProjectConfig(gitProjectID, gitlabLink, clientSecret, clientId, gitlabProjectName);
            String result = super.doDefault();
            log.debug("Exiting doDefault with a result of: " + result);
            return result;
        } else {
            getJiraServiceContext().getErrorCollection().addError(FormField.CLIENTID.getFieldName(), getJiraServiceContext().getI18nBean().getText(ERROR_INVALID_CLIENTID));
            getJiraServiceContext().getErrorCollection().addError(FormField.CLIENTSECRET.getFieldName(), getJiraServiceContext().getI18nBean().getText(ERROR_INVALID_CLIENTSECRET));
            getJiraServiceContext().getErrorCollection().addError(FormField.PROJECTID.getFieldName(), getJiraServiceContext().getI18nBean().getText(ERROR_INVALID_PROJECTID));
            getJiraServiceContext().getErrorCollection().addError(FormField.GITLABLINK.getFieldName(), getJiraServiceContext().getI18nBean().getText(ERROR_INVALID_GITLABLINK));
            getJiraServiceContext().getErrorCollection().addError(FormField.GITLABPROJECTNAME.getFieldName(), getJiraServiceContext(). getI18nBean().getText((ERROR_INVALID_GITLABPROJECTNAME)));
            log.error("Error in " + errorCollection);
            return ERROR;
        }*/
    }



    public void setClientId(String value) {
        log.debug("Setting clientId to: " + value);
        this.clientId = value;
    }

    public void setClientSecret(String value) {
        log.debug("Setting clientSecret to: " + value);
        this.clientSecret = value;
    }

    public void setGitlabProjectName(String value){
        log.debug("Setting gitlabProjectName to: " + value);
        this.gitlabProjectName = value;
    }

    public void setGitlabLink(String value) {
        log.debug("Setting gitlabLink to: " + value);
        this.gitlabLink = value;
    }


    public void setGitProjectId(String value) {
        log.debug("Setting gitProjectId to: " + value);
        this.gitProjectId = value;
    }

    public String getClientId() {
        log.debug("Getting clientId");
        return clientId;
    }

    public String getClientSecret() {
        log.debug("Getting clientSecret");
        return clientSecret;
    }

    public String getGitlabLink() {
        log.debug("Getting gitlabLink");
        return gitlabLink;
    }


    public String getGitProjectId() {
        log.debug("Getting gitProjectId");
        return gitProjectId;
    }

    public String getGitlabProjectName() {
        log.debug("Getting GitlabProjectName");
        return gitlabProjectName;
    }
}















