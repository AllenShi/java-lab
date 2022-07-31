package io.allenshi.ghaze.autoconfigure;

import io.allenshi.ghaze.service.BatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class Configurations {

    @Bean
    public BatchService batchService() {
        log.info("BatchService bean will be created");
        BatchService batchService = new BatchService();
        batchService.setAwaitTerminationSeconds(20);
        batchService.setWaitForTasksToCompleteOnShutdown(true);
        return batchService;
    }
}
