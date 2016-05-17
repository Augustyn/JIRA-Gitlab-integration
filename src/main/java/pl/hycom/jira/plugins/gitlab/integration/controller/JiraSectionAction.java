package pl.hycom.jira.plugins.gitlab.integration.controller;

import com.atlassian.jira.web.action.JiraWebActionSupport;

import java.text.SimpleDateFormat;
import java.util.*;



/**
 * A sample Action class.
 *
 * Webwork has an ActionSupport class that includes some commonly
 * encountered methods such as:
 *  doDefault, doValidation, doExecute,
 *  getErrorMessages, getErrors, getHasErrorMessages, getHasErrors,
 *  invalidInput, getRedirect
 * JIRA extends that class with JiraActionSupport and then JiraWebActionSupport.
 *
 * See the webwork-jira jar file shipped with JIRA and
 * https://svn.atlassian.com/svn/public/atlassian/vendor/webwork-1.4/trunk/src/main/webwork/action/ActionSupport.java
 *
 * Tip: many actions in JIRA extend subclasses of JiraWebActionSupport so be
 * careful about just copying them with reading their source.
 */
public class JiraSectionAction extends JiraWebActionSupport {

    // There is already a log variable in JiraActionSupport, so there's
    // no need to define one here as well. To see log messages for this
    // class, use the fully-qualified class name in log4j.properties,
    // e.g. com.consultingtoolsmiths.jira.samples.webwork.ActionAlpha

    /**
     * Nothing is needed in this constructor, but many different
     * JIRA components can be accessed using dependency injection
     *  with pico containers. See
     * http://confluence.atlassian.com/display/JIRA/Differences+between+Plugins1+and+Plugins2#DifferencesbetweenPlugins1andPlugins2-DependencyInjection
     */
    public JiraSectionAction() {
    }

    /**
     * Validate the parameters in the
     * javax.servlet.http.HttpServletRequest request. The HTML form
     * may or may not have been submitted yet since doValidation() is
     * always called when this Action's .jspa URL is invoked.
     *
     * If an error message is set and no input view exists,
     * then doExecute is not called and the view element named "error" in
     * atlassian-plugin.xml is used.
     *
     * If an error message is set and an input view does exist, then
     * the input view is used instead of the error view.
     *
     * The URL displayed in the browser doesn't change for an error,
     * just the view.
     *
     * No exceptions are thrown, instead errors and error messages are set.
     */
    protected void doValidation() {
        log.debug("Entering doValidation");
        for (Enumeration e =   getHttpRequest().getParameterNames(); e.hasMoreElements() ;) {
            String n = (String)e.nextElement();
            String[] vals =  getHttpRequest().getParameterValues(n);
            log.debug("name " + n + ": " + vals[0]);
        }

        // The underlying variable is set automatically if it is
        // present in the request parameters. This is true for both
        // form submission and HTTP GET requests.
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

        // Trigger this artificial error case by using the string
        // "bob" in the HTML input box or in the URL
        if (sClientId.indexOf("bob") != -1) {
            addErrorMessage("As expected, the text contained the string \"bob\"");
            log.debug("An error message has been set");

            // From http://www.opensymphony.com/webwork_old/build/result/web/docs/api/webwork/action/ActionSupport.html
            // addError() is used to attach an error message to a
            // particular form field.  addErrorMessage() is used to add an
            // error message to this action.
            //
            //addError("myfirstparameter", "As expected, the text contained the string \"bob\"");

            //log.debug("An error has also been set");
        }

        if (sClientSecret.indexOf("bob") != -1) {
            addErrorMessage("As expected, the text contained the string \"bob\"");
            log.debug("An error message has been set");
        }

        if (sGitlabLink.indexOf("bob") != -1) {
            addErrorMessage("As expected, the text contained the string \"bob\"");
            log.debug("An error message has been set");
        }


        // invalidInput() checks for error messages, and errors too.
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

    /**
     * This method is always called when this Action's .jspa URL is
     * invoked if there were no errors in doValidation().
     */
    protected String doExecute() throws Exception {
        log.debug("Entering doExecute");
        for (Enumeration e =   getHttpRequest().getParameterNames(); e.hasMoreElements() ;) {
            String n = (String)e.nextElement();
            String[] vals =  getHttpRequest().getParameterValues(n);
            log.debug("name " + n + ": " + vals[0]);
        }

        // Test what happens if this method throws an exception.
        // Answer: you get the exception message shown in the Standard JIRA
        // System Error page
        if (false) {
            throw new Exception("doExecute raised this exception for some reason");
        }
        return SUCCESS;
    }

    /**
     * Set up default values, if any. If you don't have default
     * values, this is not needed.
     *
     * If you want to have default values in your form's fields when it
     * is loaded, then first call this method (or one with some other
     * such name as doInit) and set the local variables. Then return
     * "input" to use the input form view, and in the form use
     * ${myfirstparameter} to call getMyfirstparameter() to load the
     * local variables.
     */
    public String doDefault() throws Exception {
        log.debug("Entering doDefault");

        // If any of these parameter names match public set methods for local
        // variables, then the local variable will have been set before this
        // method is entered.
        for (Enumeration e =   getHttpRequest().getParameterNames(); e.hasMoreElements() ;) {
            String n = (String)e.nextElement();
            String[] vals =  getHttpRequest().getParameterValues(n);
            log.debug("Parameter " + n + "=" + vals[0]);
        }

        // You could also set a local variable to have a different value
        // every time the Action is invoked here, e.g. a timestamp.

        // This should be "input". If no input view exists, that's an error.
        String result = super.doDefault();
        log.debug("Exiting doDefault with a result of: " + result);
        return result;
    }

    //
    // Start of local variables and their get and set methods
    //
    // Note: booleans should use isVariableName() not getVariableName()
    //

    /**
     * An example of a local variable that is set from HTTP request parameters.
     */

    private String clientId = "clientId default";
    private String clientSecret = "clientSecret default";
    private String gitlabLink = "gitlabLink default";
    private String projectId = "projectId default";

    /**
     * This method is automatically discovered and called by JSP and Webwork
     * if the name matches the id of a parameter passed in an HTML form.
     * The class of the parameter (String) has to match, and the
     * method has to be public or it is silently ignored.
     */


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
    /**
     * This is how the local variable is always accessed, since only this
     * action knows that its name isn't really "myfirstparameter".
     */


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


    public JiraSectionAction(String client_id, String client_secret, String gitlablink){
        this.clientId = client_id;
        this.clientSecret = client_secret;
        this.gitlabLink = gitlablink;
        this.projectId = projectId;
    }

}















