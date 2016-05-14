package pl.hycom.jira.plugins.gitlab.integration.model;
import com.atlassian.sal.api.scheduling.PluginJob;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Map;

/**
 * Created by Thorgal on 08.04.2016.
 */
public class CommitData implements PluginJob {
    @Override
    public void execute(Map<String, Object> map) {

    }
    //TO DO PIP-30 Implementacja pobierania informacji o commitach po stronie JIRY
}
