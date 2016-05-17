package pl.hycom.jira.plugins.gitlab.integration.service;

import pl.hycom.jira.plugins.gitlab.integration.model.CommitData;
import pl.hycom.jira.plugins.gitlab.integration.service.processors.ProcessorInterface;
import pl.hycom.jira.plugins.gitlab.integration.service.processors.ProcessorInterface;

import java.util.List;

/**
 * Created by Thorgal on 08.04.2016.
 */
public class ProcessorManager {
    private List<ProcessorInterface> processorsList;

    public ProcessorManager(List<ProcessorInterface> processorsList){
        this.processorsList = processorsList;
    }

    public void startProcessors(List<CommitData> commitInfoList){
        for (CommitData commitInfo : commitInfoList){
            for (ProcessorInterface processor : processorsList) {
                processor.execute(commitInfo);
            }
        }
    }
}