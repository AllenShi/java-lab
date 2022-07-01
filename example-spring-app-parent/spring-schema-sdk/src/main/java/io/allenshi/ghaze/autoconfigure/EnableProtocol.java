package io.allenshi.ghaze.autoconfigure;


import io.allenshi.ghaze.autoconfigure.spring.configuration.ProtocolBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ProtocolBeanDefinitionRegistrar.class)
public @interface EnableProtocol {

    String name() default "";

    int port();
}
