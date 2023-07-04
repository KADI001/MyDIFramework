package org.kadirov.core.service;

import org.kadirov.core.bean.BeanDefinition;

import java.util.List;

public interface DependenciesResolver {
    List<BeanDefinition<?>> resolveDependencies(List<BeanDefinition<?>> beanDefinitions);
}
