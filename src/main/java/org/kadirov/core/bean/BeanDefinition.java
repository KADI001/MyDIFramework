package org.kadirov.core.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class BeanDefinition<T> {

    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";

    public static final String STRATEGY_LAZY = "lazy";
    public static final String STRATEGY_EAGER = "eager";

    private String name;
    private String scope;
    private String strategy;
    private Class<T> type;
    private Method postConstructMethod;
    private Method preDestroyMethod;
    private Constructor<T> constructor;

    private String[] dependencies;

    public BeanDefinition() {
    }

    public BeanDefinition(String name, String scope, String strategy, Class<T> type,
                          Method initMethod, Method preDestroyMethod,
                          Constructor<T> constructor, String[] dependencies) {
        this.name = name;
        this.scope = scope;
        this.strategy = strategy;
        this.type = type;
        this.postConstructMethod = initMethod;
        this.preDestroyMethod = preDestroyMethod;
        this.constructor = constructor;
        this.dependencies = dependencies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = (Class<T>) type;
    }

    public Method getPostConstructMethod() {
        return postConstructMethod;
    }

    public void setPostConstructMethod(Method postConstructMethod) {
        this.postConstructMethod = postConstructMethod;
    }

    public Method getPreDestroyMethod() {
        return preDestroyMethod;
    }

    public void setPreDestroyMethod(Method preDestroyMethod) {
        this.preDestroyMethod = preDestroyMethod;
    }

    public Constructor<T> getConstructor() {
        return constructor;
    }

    public void setConstructor(Constructor<?> constructor) {
        this.constructor = (Constructor<T>) constructor;
    }

    public String[] getDependencies() {
        return dependencies;
    }

    public void setDependencies(String[] dependencies) {
        this.dependencies = dependencies;
    }

    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }

    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(scope);
    }

    public boolean isLazy() {
        return STRATEGY_LAZY.equals(strategy);
    }

    public boolean isEager() {
        return STRATEGY_EAGER.equals(strategy);
    }

    @SuppressWarnings("unchecked")
    public static <T> BeanDefinition<T> create(String name, String scope, String strategy, Class<?> type,
                                    Method initMethod, Method preDestroyMethod,
                                    Constructor<?> constructor, String[] dependencies){
        return new BeanDefinition<>(name, scope, strategy, (Class<T>) type, initMethod, preDestroyMethod,
                (Constructor<T>) constructor, dependencies);
    }
}
