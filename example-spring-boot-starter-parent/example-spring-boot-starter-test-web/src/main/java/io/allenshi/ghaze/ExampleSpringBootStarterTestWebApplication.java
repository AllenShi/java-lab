package io.allenshi.ghaze;

import io.allenshi.ghaze.beans.ExampleDisposableBean;
import io.allenshi.ghaze.dto.result.ConfigureInfoResult;
import io.allenshi.ghaze.service.BatchService;
import io.allenshi.ghaze.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ExampleSpringBootStarterTestWebApplication implements CommandLineRunner {

	@Autowired
	EmailService emailService;

	@Autowired
	ExampleDisposableBean exampleDisposableBean;

	public static void main(String[] args) {
		SpringApplication.run(ExampleSpringBootStarterTestWebApplication.class, args);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				log.info("shutdown hook is called");
			}
		});
	}

	@Override
	public void run(String... strings) throws Exception {
		ConfigureInfoResult configureInfoResult = emailService.configInfo();
		log.debug("The config info result, host is %s and port is %d\n", configureInfoResult.getHost(), configureInfoResult.getPort());
	}
}
