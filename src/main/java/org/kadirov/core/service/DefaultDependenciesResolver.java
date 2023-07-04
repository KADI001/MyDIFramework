package org.kadirov.core.service;

import org.kadirov.core.bean.BeanDefinition;
import org.kadirov.core.exception.ResolvingBeanDefinitionException;
import org.kadirov.core.util.PackageUtil;
import org.kadirov.core.util.ReflectionUtil;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class DefaultDependenciesResolver implements DependenciesResolver {

    private final List<BeanDefinition<?>> beanDefinitions;

    public DefaultDependenciesResolver() {
        beanDefinitions = new ArrayList<>();
    }

    @Override
    public List<BeanDefinition<?>> resolveDependencies(List<BeanDefinition<?>> beanDefinitions) {
        this.beanDefinitions.clear();
        for (BeanDefinition<?> beanDefinition : beanDefinitions) {
            String[] dependencies = new String[beanDefinition.getConstructor().getParameterCount()];
            int inc = 0;
            for (Parameter parameter : beanDefinition.getConstructor().getParameters()) {
                for (BeanDefinition<?> definition : beanDefinitions) {
                    if(beanDefinition == definition){
                        continue;
                    }

                    boolean equals = false;

                    if(parameter.getType().isInterface()){
                        if(parameter.getType().isAssignableFrom(definition.getType())){
                            equals = true;
                        }
                    }else{
                        if(parameter.getType().equals(definition.getType())){
                            equals = true;
                        }
                    }

                    if(equals){
                        dependencies[inc] = definition.getName();
                        inc++;
                    }
                }
            }

            if(beanDefinition.getConstructor().getParameterCount() != inc){
                throw new ResolvingBeanDefinitionException("Couldn't resolve dependencies for " + beanDefinition.getName()
                        + " with constructor " + beanDefinition.getConstructor());
            }

            beanDefinition.setDependencies(dependencies);
            this.beanDefinitions.add(beanDefinition);
        }
        return this.beanDefinitions;
    }
}
