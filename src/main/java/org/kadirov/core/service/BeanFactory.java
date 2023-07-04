package org.kadirov.core.service;

public interface BeanFactory {
    <T> T getBean(Class<T> clazz);
    <T> T getBean(String name);
    String[] getBeansNames();
}