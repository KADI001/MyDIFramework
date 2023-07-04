package org.kadirov.core.service;

import org.kadirov.core.annotation.*;
import org.kadirov.core.bean.BeanDefinition;
import org.kadirov.core.comparator.AutowiredConstructorComparator;
import org.kadirov.core.exception.NotFoundConstructorException;
import org.kadirov.core.exception.UnDefinedAutowiredException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultBeanDefinitionResolver implements BeanDefinitionResolver {

    private final List<BeanDefinition<?>> beanDefinitions;

    public DefaultBeanDefinitionResolver() {
        this.beanDefinitions = new ArrayList<>();
    }

    @Override
    public List<BeanDefinition<?>> resolve(List<Class<?>> classes) {
        beanDefinitions.clear();

        for (Class<?> clazz : classes) {
            if (clazz.isInterface() || clazz.isEnum() || clazz.isAnnotation()) {
                continue;
            }

            if(clazz.isAnnotationPresent(Component.class)){
                BeanDefinition<?> beanDefinition = new BeanDefinition<>();

                beanDefinition.setType(clazz);

                String name = getClassName(clazz);

                Qualifier qualifierAnnotation =
                        clazz.getAnnotation(Qualifier.class);

                if(qualifierAnnotation != null){
                    name = qualifierAnnotation.value();
                }

                beanDefinition.setName(name);

                String scope = BeanDefinition.SCOPE_SINGLETON;
                Scope scopeAnnotation =
                        clazz.getAnnotation(Scope.class);

                if(scopeAnnotation != null){
                    scope = scopeAnnotation.value();
                }

                beanDefinition.setScope(scope);

                String strategy = BeanDefinition.STRATEGY_LAZY;
                Strategy strategyAnnotation =
                        clazz.getAnnotation(Strategy.class);

                if(strategyAnnotation != null){
                    strategy = strategyAnnotation.value();
                }

                beanDefinition.setStrategy(strategy);

                Method postConstructMethod = getPostConstructMethod(clazz);
                beanDefinition.setPostConstructMethod(postConstructMethod);

                Method preDestroyMethod = getPreDestroyMethod(clazz);
                beanDefinition.setPreDestroyMethod(preDestroyMethod);

                Constructor<?> constructor = getTargetConstructor(clazz);
                beanDefinition.setConstructor( constructor);

                beanDefinitions.add(beanDefinition);
            }
        }

        return beanDefinitions;
    }

    private static String getClassName(Class<?> clazz) {
        String[] className = clazz.getName().split("\\.");
        return className[className.length - 1];
    }

    private Constructor<?> getTargetConstructor(Class<?> clazz) {
        Constructor<?>[] clazzConstructors = clazz.getDeclaredConstructors();
        List<Constructor<?>> autowiredConstructors = Arrays.stream(clazzConstructors)
                .filter(constructor ->
                        constructor.isAnnotationPresent(Autowired.class))
                .toList();

        if(autowiredConstructors.size() > 1){
            throw new UnDefinedAutowiredException("Class " + clazz.getName() +
                    " has more than one constructor with @Autowired annotation");
        }

        if(autowiredConstructors.size() == 1){
            return autowiredConstructors.get(0);
        }

        if(clazzConstructors.length == 1){
            return clazzConstructors[0];
        }

        if(clazzConstructors.length > 1){
            List<Constructor<?>> orderedConstructors = Arrays.stream(clazzConstructors)
                    .sorted(new AutowiredConstructorComparator())
                    .toList();
            return orderedConstructors.get(0);
        }

        throw new NotFoundConstructorException(
                "Couldn't find any suitable constructor in class with name "
                + clazz.getName());
    }

    private Method getPostConstructMethod(Class<?> clazz) {
        List<Method> methodVoidReturnNonParamsByAnnotation =
                getMethodVoidReturnNonParamsByAnnotation(clazz, PostConstruct.class);
        return methodVoidReturnNonParamsByAnnotation.size() > 0 ?
                methodVoidReturnNonParamsByAnnotation.get(0) : null;
    }

    private Method getPreDestroyMethod(Class<?> clazz) {
        List<Method> methodVoidReturnNonParamsByAnnotation =
                getMethodVoidReturnNonParamsByAnnotation(clazz, PreDestroy.class);
        return methodVoidReturnNonParamsByAnnotation.size() > 0 ?
                methodVoidReturnNonParamsByAnnotation.get(0) : null;
    }

    private List<Method> getMethodVoidReturnNonParamsByAnnotation(Class<?> clazz,
                                                                  Class<? extends Annotation> annot) {
        List<Method> methods = new ArrayList<>();
        for (Method method : clazz.getMethods()) {
            if(!isVoidReturnAndNonParams(method)){
                continue;
            }

            if(method.isAnnotationPresent(annot)){
                methods.add(method);
            }
        }

        return methods;
    }

    private static boolean isVoidReturnAndNonParams(Method method) {
        return (method.getReturnType() == void.class
                || method.getReturnType() == Void.class)
                && method.getParameterCount() == 0;
    }
}