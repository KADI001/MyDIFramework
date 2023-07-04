package org.kadirov.service;

import org.kadirov.core.annotation.Autowired;
import org.kadirov.core.annotation.Component;
import org.kadirov.core.annotation.Qualifier;
import org.kadirov.core.annotation.Scope;

@Component
@Scope("prototype")
@Qualifier("MyCustomName")
public class FirstServiceImpl implements FirstService {

    private String temp1;

    private Integer temp2;

    private Long temp3;

    public FirstServiceImpl() {
    }

    public FirstServiceImpl(String temp1) {
        this.temp1 = temp1;
    }

    @Autowired
    public FirstServiceImpl(SecondService service) {
    }

    public FirstServiceImpl(String temp1, Integer temp2) {
        this.temp1 = temp1;
        this.temp2 = temp2;
    }

    @Override
    public void doSomething() {
        temp1 = "3";
        temp1 = "54";
        temp2 = 34;
        System.out.println("DOING SOMETHING IN FIRSTSERVICE");
    }
}
