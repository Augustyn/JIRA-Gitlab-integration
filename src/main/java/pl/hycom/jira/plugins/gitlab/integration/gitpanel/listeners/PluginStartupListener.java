package pl.hycom.jira.plugins.gitlab.integration.gitpanel.listeners;

import com.atlassian.sal.api.lifecycle.LifecycleAware;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.api.ProcessorInterface;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl.ProcessorManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Thorgal on 26.04.2016.
 */
public class PluginStartupListener implements LifecycleAware {
    @Autowired
    ProcessorManager processorManager;

    @Setter
    private String referenceToPackagePath = "pl.hycom.jira.plugins.gitlab.integration.gitpanel";

    @Autowired
    private ApplicationContext context;

    @Override
    public void onStart() {
        List<Class<? extends ProcessorInterface>> processorsList = new ArrayList<>();

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

        processorManager.setProcessorsList(processorsList);
    }

    @Override
    public void onStop() {

    }
}
