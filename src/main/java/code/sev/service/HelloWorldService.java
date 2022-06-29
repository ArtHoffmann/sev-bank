package code.sev.service;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HelloWorldService {

    public String helloWorld() {
        return "Hello World ist nice";
    }
}
