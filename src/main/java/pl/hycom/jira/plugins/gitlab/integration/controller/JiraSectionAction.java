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
package pl.hycom.jira.plugins.gitlab.integration.controller;

import com.atlassian.jira.project.Project;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigEntity;
import pl.hycom.jira.plugins.gitlab.integration.dao.ConfigManagerDao;
import pl.hycom.jira.plugins.gitlab.integration.model.FormField;
import pl.hycom.jira.plugins.gitlab.integration.model.GitlabProject;
import pl.hycom.jira.plugins.gitlab.integration.service.GitlabService;
import pl.hycom.jira.plugins.gitlab.integration.service.Validator;
import pl.hycom.jira.plugins.gitlab.integration.validation.ErrorCollection;

import java.util.*;

@Log4j
@RequiredArgsConstructor
public class JiraSectionAction extends JiraWebActionSupport {


    private static final String ERROR_INVALID_CLIENTID = "jirasection.action.error.invalid.cliendid";
    private static final String ERROR_INVALID_CLIENTSECRET = "jirasection.action.error.invalid.cliendsecret";
    private static final String ERROR_INVALID_PROJECTID = "jirasection.action.error.invalid.projectid";
    private static final String ERROR_INVALID_GITLABLINK = "jirasection.action.error.invalid.gitlablink";
    private static final String ERROR_INVALID_GITLABPROJECTNAME = "jirasection.action.error.invalid.gitlabprojectname";
    /* injected through constructor: */
    @Autowired private final Validator validator;
    @Autowired private final ConfigManagerDao confManager;
    @Autowired private final GitlabService gitlabService;

    private String clientId;
    private String clientSecretToken;
    private String gitlabHost;
    private String gitlabProjectName;
    private String gitProjectId;
    private Project project;
    private Long projectId;
    private ConfigEntity projectConfig;

    private ErrorCollection doInternalValidate() {
        Map<FormField, String> paramMap = new HashMap<>();

        paramMap.put(FormField.CLIENTID, clientId);
        paramMap.put(FormField.CLIENTSECRET, clientSecretToken);
        paramMap.put(FormField.GITLABLINK, gitlabHost);
        paramMap.put(FormField.PROJECTID, gitProjectId);
        paramMap.put(FormField.GITLABPROJECTNAME, gitlabProjectName);

        final ErrorCollection validate = validator.validate(paramMap);
        validate.getErrorMessages().forEach(e->this.getErrorMessages().add(this.getI18nHelper().getText(e)));
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

        log.debug("The local variable client_id  is currently set to: " + clientId);
        log.debug("The local variable client_secret is currently set to: " + clientSecretToken);
        log.debug("The local variable gitlablink is currently set to: " + gitlabHost);
        log.debug("The local variable gitlabProjectName is currently set to: " + gitlabProjectName);


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
        log.info("Saving project: '" + this.projectId + "' configuration.");
        String result = validateProject();
        if (ERROR.equals(result)) {
            return result; //No project id in context, no use to continue.
        }
        final ConfigEntity configEntity = confManager.updateProjectConfig(this.projectId, gitlabHost, clientSecretToken, clientId, gitlabProjectName);
        return (configEntity != null ? SUCCESS : ERROR);
    }

    public String doSave() throws Exception {
        String result = validateProject();
        if (ERROR.equals(result) || projectId == null) {
            addError("gitlab-project", "Couldn't find Gitlab project id based on provided data. Please verify Gitlab project name and credentials", Reason.VALIDATION_FAILED);
            return INPUT; //No project id in context, no use to continue.
        }
        ConfigEntity config = null;
        try {
            config = confManager.updateProjectConfig(projectId, gitlabHost, clientSecretToken, clientId, gitlabProjectName);
        } catch (Exception e) {
            log.error("Couldn't save project configuration, with message: '" + e.getMessage() + "'. Enable debug for more info.");
            log.debug("Stack:", e);
        }
        if (config == null) {
            addError("config", "Couldn't save project configuration. Please contact administrator.", Reason.SERVER_ERROR);
            return ERROR;
        }
        try {
            final Optional<GitlabProject> gitlabProject = gitlabService.getGitlabProject(config);
            if (!gitlabProject.isPresent()) {
                addError("gitlab-project", "Couldn't find Gitlab project id based on provided data. Please verify Gitlab project name and credentials", Reason.VALIDATION_FAILED);
                return ERROR;
            }
        } catch (Exception e) {
            addError("gitlab-project", "Couldn't contact Gitlab. Please verify host name and credentials.");
            log.error("Exception occurred while contacting: " + gitlabHost +" with message: " + e.getMessage() +". Enable debug for more info");
            log.debug("Gitlab host: " + gitlabHost +", stack:", e);
        }
        final ErrorCollection errorCollection = doInternalValidate();
        return SUCCESS;
    }

    public String validateProject() throws Exception {
        this.project = getSelectedProject();
        final String rawProjectId = getHttpRequest().getParameter("projectId");
        Long paramProjectId = project != null ? project.getId() : null;
        if (StringUtils.isNotBlank(rawProjectId)) {
            try {
                paramProjectId = Long.valueOf(rawProjectId);
            } catch (NumberFormatException e) {
                addError("project", "No project or project id in context. Cannot continue", Reason.VALIDATION_FAILED);
                return ERROR;
            }
            if (!paramProjectId.equals(project != null ? project.getId() : paramProjectId)) {
                addError("project", "Provided parameter project ID differs from project context. Cannot continue", Reason.VALIDATION_FAILED);
            }
        }
        this.projectId = paramProjectId;
        return INPUT;
    }

    public String doDefault() throws Exception {
        log.debug("Entering doDefault");
        String result = validateProject();
        if (ERROR.equals(result) || this.projectId == null) {
            return result; //No project in context. it has no sense to continue.
        }
        final ConfigEntity projectConfig = confManager.getProjectConfig(this.projectId);
        if (projectConfig != null) {
            this.clientId = projectConfig.getClientId();
            this.gitlabProjectName = projectConfig.getGitlabProjectName();
            this.gitlabHost = projectConfig.getGitlabURL();
            this.clientSecretToken = projectConfig.getGitlabSecretToken();
            this.projectConfig = projectConfig;
        }
        log.debug("Exiting doDefault with a result of: " + result + " with project: " + project);
        return result;
        //

        /*if (errorCollection.isEmpty() && gitlabCommunicationManagerDao.findGitlabProjectId(gitProjectId)>0){
            confManager.updateProjectConfig(gitProjectID, gitlabHost, clientSecretToken, clientId, gitlabProjectName);
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

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getClientSecretToken() {
        return clientSecretToken;
    }
    public void setClientSecretToken(String clientSecretToken) {
        this.clientSecretToken = clientSecretToken;
    }
    public String getGitlabHost() {
        return gitlabHost;
    }
    public void setGitlabHost(String gitlabHost) {
        this.gitlabHost = gitlabHost;
    }
    public String getGitProjectId() {
        return gitProjectId;
    }
    public void setGitProjectId(String gitProjectId) {
        this.gitProjectId = gitProjectId;
    }
    public String getGitlabProjectName() {
        return gitlabProjectName;
    }
    public void setGitlabProjectName(String gitlabProjectName) {
        this.gitlabProjectName = gitlabProjectName;
    }
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }
}















