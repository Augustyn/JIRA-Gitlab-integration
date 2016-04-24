package pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl;


import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.api.ProcessorInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Thorgal on 08.04.2016.
 */
//@ComponentScan
//@Configuration
//@PropertySource("classpath:main\\resources\\jira-gitlab-plugin.properties")
public class ProcessorManager {
    //@Value("${klucz}")
    @Value("#{contentProps['klucz']}")
    private String referenceToPackagePath;

    public List<ProcessorInterface> getProcessorsList() {
        return processorsList;
    }

    public void setProcessorsList(List<ProcessorInterface> processorsList) {
        this.processorsList = processorsList;
    }

    private List<ProcessorInterface> processorsList = new ArrayList<>();

    public void startProcessors(List<CommitData> commitInfoList) {
        for (CommitData commitInfo : commitInfoList) {
            for (ProcessorInterface processor : processorsList) {
                processor.execute(commitInfo);
            }
        }
    }

    public void getImplementingClasses(){
//        BeanDefinitionRegistry bdr = new SimpleBeanDefinitionRegistry();
//        ClassPathBeanDefinitionScanner s = new ClassPathBeanDefinitionScanner(bdr);
//
//        TypeFilter tf = new AssignableTypeFilter(ProcessorInterface.class);
//        s.setIncludeAnnotationConfig(false);
//        s.addIncludeFilter(tf);
//        s.scan(referenceToPackagePath);
//        String[] beans = bdr.getBeanDefinitionNames();
//        for (String bean: beans) {
//            System.out.println(bean.toString());
//        }
//        System.out.println(beans);
        System.out.println(referenceToPackagePath);
    }
//    public Set<? extends ProcessorInterface> getImplementingClasses() {
//        Reflections reflections = new Reflections(referenceToPackagePath);
//        Set<Class<? extends ProcessorInterface>> classes = reflections.getSubTypesOf(ProcessorInterface.class);
//        classes.iterator().next().getClasses()[0].
//        processorsList.addAll(classes);
//        return classes;
//    }
//    public <T> List<Class<? extends T>> findAllMatchingTypes(Class<T> toFind) {
//        foundClasses = new ArrayList<Class<?>>();
//        List<Class<? extends T>> returnedClasses = new ArrayList<Class<? extends T>>();
//        this.toFind = toFind;
//        walkClassPath();
//        for (Class<?> clazz : foundClasses) {
//            returnedClasses.add((Class<? extends T>) clazz);
//        }
//        return returnedClasses;
//    }

}
