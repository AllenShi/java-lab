package io.allenshi.ghaze.autoconfigure;

import io.allenshi.ghaze.service.EmailService;
import io.allenshi.ghaze.service.impl.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AutoConfiguration Class
 *
 */
@Configuration
@ConditionalOnClass(EmailService.class)
@EnableConfigurationProperties(value = MailProperties.class)
public class MailSenderAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(MailSenderAutoConfiguration.class);

    /**
     * EmailService
     *
     * @return {@link EmailService}
     */
    @Bean
    @ConditionalOnMissingBean
    public EmailService emailService() {
        logger.info("Config ExampleService Start...");
        EmailServiceImpl service = new EmailServiceImpl(properties.getHost(), properties.getPort());
        logger.info("Config ExampleService End.");
        return service;
    }


    @Autowired
    private MailProperties properties;

}
