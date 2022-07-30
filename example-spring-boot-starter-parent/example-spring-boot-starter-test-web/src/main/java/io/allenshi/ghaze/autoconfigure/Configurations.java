package io.allenshi.ghaze.autoconfigure;

import io.allenshi.ghaze.service.BatchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurations {

    @Bean
    public BatchService batchService() {
        BatchService batchService = new BatchService();
        batchService.setAwaitTerminationSeconds(20);
        batchService.setWaitForTasksToCompleteOnShutdown(true);
        return batchService;
    }
}
