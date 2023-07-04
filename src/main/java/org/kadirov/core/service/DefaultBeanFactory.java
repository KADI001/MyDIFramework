package org.kadirov.core.service;

import org.kadirov.core.bean.BeanDefinition;
import org.kadirov.core.comparator.AutowiredConstructorComparator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultBeanFactory implements BeanFactory{

    private final List<BeanDefinition<?>> beanDefinitions;

    private final Map<String, Object> beanMap;

    public DefaultBeanFactory(List<BeanDefinition<?>> beanDefinitions) {
        this.beanDefinitions = beanDefinitions;
        this.beanMap = new HashMap<>();

        initEagerBeans();
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T getBean(String name) {
        List<BeanDefinition<?>> beanDefinitionList = beanDefinitions.stream()
                .filter(beanDefinition -> beanDefinition.getName().equals(name))
                .toList();

        if(beanDefinitionList.isEmpty()){
            return null;
        }

        BeanDefinition<?> beanDefinition = beanDefinitionList.get(0);

        if (beanMap.containsKey(name) && beanDefinition.isSingleton()) {
            return (T) beanMap.get(name);
        }

        return createNewBean(beanDefinition);
    }

    @Override
    public String[] getBeansNames() {
        return beanMap.keySet().toArray(String[]::new);
    }

    private <T> T createNewBean(BeanDefinition<?> beanDefinition) {
        Object[] ctrArgs = new Object[beanDefinition.getDependencies().length];
        int inc = 0;

        for (String dependency : beanDefinition.getDependencies()) {
            ctrArgs[inc] = getBean(dependency);
            inc++;
        }

        Object bean;

        try {
            if(inc > 0){
                bean = beanDefinition.getConstructor().newInstance(ctrArgs);
            }else{
                bean = beanDefinition.getConstructor().newInstance();
            }

            if(beanDefinition.isSingleton()){
                beanMap.put(beanDefinition.getName(), bean);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }


        return (T) bean;
    }

    private void initEagerBeans(){
        List<BeanDefinition<?>> eagerBeanDefinitions = beanDefinitions.stream()
                .filter(BeanDefinition::isEager)
                .filter(BeanDefinition::isSingleton)
                .sorted((o1, o2) -> new AutowiredConstructorComparator().compare(o1.getConstructor(), o2.getConstructor()))
                .toList();

        eagerBeanDefinitions.forEach(this::createNewBean);
    }
}