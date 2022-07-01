package net.sjl.spring.logger.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.sjl.spring.logger.bean.Worker;

import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerApplicationTests {

  @Autowired
  private Worker worker; 

  @Test
  public void shouldApplyCustomAnnotation() throws InterruptedException {
    worker.serve();
  }
}
