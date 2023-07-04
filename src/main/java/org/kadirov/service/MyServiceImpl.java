package org.kadirov.service;

import org.kadirov.core.annotation.Autowired;
import org.kadirov.core.annotation.Component;
import org.kadirov.core.annotation.PostConstruct;
import org.kadirov.core.annotation.Strategy;

@Component
@Strategy("eager")
public class MyServiceImpl implements MyService {

    private FirstService firstService;
    private SecondService secondService;

    @Autowired
    public MyServiceImpl(FirstService firstService, SecondService secondService) {
        this.firstService = firstService;
        this.secondService = secondService;
    }

    @PostConstruct
    public void init(){
        System.out.println("Initial");
    }

    @PostConstruct
    public void init2(int a){
        System.out.println("Initial2");
    }

    @Override
    public void doSomething() {

    }
}
