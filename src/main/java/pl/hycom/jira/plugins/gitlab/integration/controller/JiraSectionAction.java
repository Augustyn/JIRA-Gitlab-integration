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
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigManagerDaoImpl;
import pl.hycom.jira.plugins.gitlab.integration.model.FormField;
import pl.hycom.jira.plugins.gitlab.integration.validation.ErrorCollection;
import pl.hycom.jira.plugins.gitlab.integration.service.Validator;

import java.util.*;




public class JiraSectionAction extends JiraWebActionSupport {


    private static final String ERROR_INVALID_CLIENTID = "jirasection.action.error.invalid.cliendid";
    private static final String ERROR_INVALID_CLIENTSECRET = "jirasection.action.error.invalid.cliendsecret";
    private static final String ERROR_INVALID_PROJECTID = "jirasection.action.error.invalid.projectid";
    private static final String ERROR_INVALID_GITLABLINK = "jirasection.action.error.invalid.gitlablink";

    private Validator validator;
    private String clientId = "clientId default";
    private String clientSecret = "clientSecret default";
    private String gitlabLink = "gitlabLink default";
    private String projectId = "projectId default";


    public JiraSectionAction() {
    }

    public JiraSectionAction(String client_id, String client_secret, String gitlablink){
        this.clientId = client_id;
        this.clientSecret = client_secret;
        this.gitlabLink = gitlablink;
        this.projectId = projectId;
    }


    protected void doValidation() {
        log.warn("Entering doValidation");
        for (Enumeration e =   getHttpRequest().getParameterNames(); e.hasMoreElements() ;) {
            String n = (String)e.nextElement();
            String[] vals =  getHttpRequest().getParameterValues(n);
            log.debug("name " + n + ": " + vals[0]);
        }

        String sClientId = getClientId();
        String sClientSecret = getClientSecret();
        String sGitlabLink = getGitlabLink();
        log.debug("The local variable client_id  is currently set to: " + sClientId);
        log.debug("The local variable client_secret is currently set to: " + sClientSecret);
        log.debug("The local variable gitlablink is currently set to: " + sGitlabLink);

        if (sClientId == null) {
            addErrorMessage("The local variable client_id didn't get set");
            return;
        }

        if (sClientSecret == null) {
            addErrorMessage("The local variable client_secret didn't get set");
            return;
        }

        if (sGitlabLink == null) {
            addErrorMessage("The local variable gitlablink didn't get set");
            return;
        }

        if (sClientId.indexOf("bob") != -1) {
            addErrorMessage("As expected, the text contained the string \"bob\"");
            log.debug("An error message has been set");


        }

        if (sClientSecret.indexOf("bob") != -1) {
            addErrorMessage("As expected, the text contained the string \"bob\"");
            log.debug("An error message has been set");
        }

        if (sGitlabLink.indexOf("bob") != -1) {
            addErrorMessage("As expected, the text contained the string \"bob\"");
            log.debug("An error message has been set");
        }

        if (invalidInput()) {
            for (Iterator it = getErrorMessages().iterator(); it.hasNext();) {
                String msg = (String)it.next();
                log.debug("Error message during validation: " + msg);
            }

            for (Iterator it2 = getErrors().entrySet().iterator(); it2.hasNext();) {
                Map.Entry entry = (Map.Entry)it2.next();
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
        Map<FormField, String> paramMap = new HashMap<>();

        String sClientId = getClientId();
        String sClientSecret = getClientSecret();
        String sGitlabLink = getGitlabLink();
        String sProjectId = getProjectId();
        paramMap.put(FormField.CLIENTID, sClientId);
        paramMap.put(FormField.CLIENTSECRET, sClientSecret);
        paramMap.put(FormField.GITLABLINK, sGitlabLink);
        paramMap.put(FormField.PROJECTID, sProjectId);

        ErrorCollection errorCollection = validator.validate(paramMap);
        if (errorCollection.isEmpty()){
            ConfigManagerDaoImpl myConfManager = new ConfigManagerDaoImpl();
            myConfManager.updateProjectConfig(sProjectId, sGitlabLink, sClientSecret, sClientId);
            String result = super.doDefault();


            log.debug("Exiting doDefault with a result of: " + result);
            return result;
        }else{
            getJiraServiceContext().getErrorCollection().addError(FormField.CLIENTID.getFieldName(), getJiraServiceContext().getI18nBean().getText(ERROR_INVALID_CLIENTID));
            getJiraServiceContext().getErrorCollection().addError(FormField.CLIENTSECRET.getFieldName(), getJiraServiceContext().getI18nBean().getText(ERROR_INVALID_CLIENTSECRET));
            getJiraServiceContext().getErrorCollection().addError(FormField.PROJECTID.getFieldName(), getJiraServiceContext().getI18nBean().getText(ERROR_INVALID_PROJECTID));
            getJiraServiceContext().getErrorCollection().addError(FormField.GITLABLINK.getFieldName(), getJiraServiceContext().getI18nBean().getText(ERROR_INVALID_GITLABLINK));
            return ERROR;
        }


    }



    public void setClientId(String value) {
        log.debug("Setting clientId to: " + value);
        this.clientId = value;
    }

    public void setClientSecret(String value) {
        log.debug("Setting clientSecret to: " + value);
        this.clientSecret = value;
    }

    public void setGitlabLink(String value) {
        log.debug("Setting gitlabLink to: " + value);
        this.gitlabLink = value;
    }


    public void setProjectId(String value) {
        log.debug("Setting projectId to: " + value);
        this.projectId = value;
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

    public String getProjectId() {
        log.debug("Getting projectId");
        return projectId;
    }
}















