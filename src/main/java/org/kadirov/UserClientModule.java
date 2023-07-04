package org.kadirov;

import org.kadirov.core.annotation.Autowired;
import org.kadirov.core.annotation.Component;
import org.kadirov.service.FirstService;
import org.kadirov.service.SecondService;

@Component
public class UserClientModule {

    @Autowired
    private FirstService firstService;

    @Autowired
    private SecondService secondService;

    public UserClientModule() {
    }

    public FirstService getFirstService() {
        return firstService;
    }

    public void setFirstService(FirstService firstService) {
        this.firstService = firstService;
    }

    public SecondService getSecondService() {
        return secondService;
    }

    public void setSecondService(SecondService secondService) {
        this.secondService = secondService;
    }

    public static void main(String[] args) {
        UserClientModule userClientModule = new UserClientModule();
        userClientModule.getFirstService().doSomething();
        userClientModule.getSecondService().doSomething();
        System.out.println("DONE!!!");
    }
}