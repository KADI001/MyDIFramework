package org.kadirov.core.service;

import org.kadirov.core.bean.BeanDefinition;
import org.kadirov.core.exception.PackageDoesntExistException;

import java.util.List;

public interface PackageScanner {
    List<Class<?>> scan(String rootPackage) throws PackageDoesntExistException;
}
