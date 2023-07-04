package org.kadirov.core;

import org.kadirov.UserClientModule;
import org.kadirov.core.bean.BeanDefinition;
import org.kadirov.core.service.*;
import org.kadirov.service.FirstService;
import org.kadirov.service.SecondService;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        run(Main.class);
    }

    public static void run(Class<?> programClass){
        String rootPackage = programClass.getProtectionDomain()
                .getCodeSource().getLocation().getPath();

        PackageScanner scanner = new DefaultPackageScanner();

        List<Class<?>> allClasses = scanner.scan(rootPackage);

        BeanDefinitionResolver beanDefinitionResolver =
                new DefaultBeanDefinitionResolver();

        List<BeanDefinition<?>> beanDefinitions = beanDefinitionResolver.resolve(allClasses);

        DependenciesResolver dependenciesResolver = new DefaultDependenciesResolver();

        List<BeanDefinition<?>> resolvedDependencies = dependenciesResolver.resolveDependencies(beanDefinitions);

        BeanFactory beanFactory = new DefaultBeanFactory(resolvedDependencies);

        SecondService secondService = beanFactory.getBean("SecondServiceImpl");
        secondService.doSomething();
        FirstService firstService = beanFactory.getBean("FirstServiceImpl");
        firstService.doSomething();
        FirstService firstService2 = beanFactory.getBean("FirstServiceImpl");

        UserClientModule userClientModule = beanFactory.getBean("UserClientModule");

        Arrays.stream(beanFactory.getBeansNames()).forEach(System.out::println);
    }
}