package io.allenshi.ghaze;

import io.allenshi.ghaze.autoconfigure.Application;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassPathXmlApplicationContextRunner {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("gracehaze.xml");
        context.start();
        Application applicationConfig = (Application)context.getBean("provider");
        System.out.format("Application bean info, name: %s, class: %s\n", applicationConfig.getName(), applicationConfig.getClass());

    }
}
