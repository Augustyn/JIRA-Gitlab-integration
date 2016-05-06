package pl.hycom.jira.plugins.gitlab.integration.gitpanel.api;

import pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl.Commit;
/**
 * Created by Thorgal on 08.04.2016.
 */
public interface ProcessorInterface {
    public void execute(Commit commitInfo);
}
