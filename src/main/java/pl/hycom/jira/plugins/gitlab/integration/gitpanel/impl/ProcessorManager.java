package pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl;


import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.api.ProcessorInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thorgal on 08.04.2016.
 */
@Service
public class ProcessorManager {
    @Setter
    private List<Class<? extends ProcessorInterface>> processorsList = new ArrayList<>();

    public void startProcessors(List<CommitData> commitInfoList) {
        for (CommitData commitInfo : commitInfoList) {
            for (Class<? extends ProcessorInterface> processor : processorsList) {
                ProcessorInterface interf = processor.cast(ProcessorInterface.class);
                interf.execute(commitInfo);
            }
        }
    }

}
