package net.sjl.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import net.sjl.spring.logger.bean.Worker;

@SpringBootApplication
public class RosemaryApplication {

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(RosemaryApplication.class, args);
    // Worker worker = context.getBean(Worker.class);
    Worker worker = (Worker)context.getBean("worker");
    try {
      worker.serve();
    } catch (Exception e) {}
  }
}
