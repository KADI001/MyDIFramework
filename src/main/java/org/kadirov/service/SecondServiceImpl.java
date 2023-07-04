package org.kadirov.service;

import org.kadirov.core.annotation.Component;

@Component
public class SecondServiceImpl implements SecondService {

    private String data;

    @Override
    public void doSomething() {
        data = "DOING SOMETHING IN SECONDSERVICE";
        System.out.println(data);
    }
}
