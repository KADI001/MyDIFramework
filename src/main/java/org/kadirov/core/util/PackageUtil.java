package org.kadirov.core.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PackageUtil {
    public static <T> List<Class<? extends T>> getClasses(String packageName) {
        String packagePath = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL packageURL = classLoader.getResource(packagePath);
        File packageDir = new File(packageURL.getFile());
        File[] classFiles = packageDir.listFiles((dir, fileName) -> fileName.endsWith(".class"));

        List<Class<? extends T>> result = new ArrayList<>();
        for (File classFile : classFiles) {
            String className = packageName + "." + classFile.getName().replace(".class", "");
            try {
                Class<?> clazz = Class.forName(className);
                Class<? extends T> classType = (Class<? extends T>) clazz;
                result.add(classType);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    public static <T> List<Class<? extends T>> getClasses(Class<?> packageClass) {
        return getClasses(packageClass.getPackageName());
    }
}
