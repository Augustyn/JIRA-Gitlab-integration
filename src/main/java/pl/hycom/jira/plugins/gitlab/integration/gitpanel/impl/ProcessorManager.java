package pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl;


import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.api.ProcessorInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Thorgal on 08.04.2016.
 */
@Service
public class ProcessorManager {
    @Setter
    private String referenceToPackagePath = "pl.hycom.jira.plugins.gitlab.integration.gitpanel";
    @Autowired
    private ApplicationContext context;
    @Setter
    private List<Class<? extends ProcessorInterface>> processorsList = new ArrayList<>();

    public void startProcessors(List<CommitData> commitInfoList) {
        for (CommitData commitInfo : commitInfoList) {
            for (Class<? extends ProcessorInterface> processor : processorsList) {
                ProcessorInterface interf = (ProcessorInterface) processor.cast(ProcessorInterface.class);
                interf.execute(commitInfo);
            }
        }
    }

    public void getImplementingClasses() {
        BeanDefinitionRegistry bdr = new SimpleBeanDefinitionRegistry();
        ClassPathBeanDefinitionScanner s = new ClassPathBeanDefinitionScanner(bdr);

        TypeFilter tf = new AssignableTypeFilter(ProcessorInterface.class);
        s.setIncludeAnnotationConfig(false);
        s.resetFilters(false);
        s.addIncludeFilter(tf);
        s.scan(referenceToPackagePath);
        String[] beans = bdr.getBeanDefinitionNames();
        for (String name : Arrays.asList(beans)) {
            Object bean =
                    context.getBean(name);
            if (bean instanceof ProcessorInterface) {
                Class<? extends ProcessorInterface> myBean = (Class<? extends ProcessorInterface>) bean;
                processorsList.add(myBean);
            }
        }
    }

}
