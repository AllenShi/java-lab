package io.allenshi.ghaze.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static io.allenshi.ghaze.autoconfigure.MailProperties.DEFAULT_PREFIX;

/**
 * 配置属性项
 * Properties defined in the MailProperties file are the default properties for MailSenderAutoConfiguration class while initializing beans
 * Spring Boot allows us to override these configuration properties using application.properties file.
 * To override default port, we need to add the following entry in our application.properties file.
 * example.mail.host=localhost .  (prefix+property name)
 * example.mail.port=445 .  (prefix+property name)
 */
@Data
@ConfigurationProperties(prefix = DEFAULT_PREFIX)
public class MailProperties {

    public static final String DEFAULT_PREFIX = "example.mail";

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private String host;

    private Integer port;

}
