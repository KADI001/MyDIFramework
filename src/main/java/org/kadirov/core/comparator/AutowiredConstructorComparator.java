package org.kadirov.core.comparator;

import org.kadirov.core.bean.BeanDefinition;

import java.lang.reflect.Constructor;
import java.util.Comparator;

public class AutowiredConstructorComparator implements Comparator<Constructor<?>> {
    @Override
    public int compare(Constructor<?> o1, Constructor<?> o2) {
        return Integer.compare(o1.getParameterCount(), o2.getParameterCount());
    }
}