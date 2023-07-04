package org.kadirov.core.service;

import org.kadirov.core.bean.BeanDefinition;

import java.util.List;

public interface BeanDefinitionResolver {
    List<BeanDefinition<?>> resolve(List<Class<?>> classes);
}
