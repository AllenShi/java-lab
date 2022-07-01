package io.allenshi.ghaze.autoconfigure.spring.configuration;

import io.allenshi.ghaze.autoconfigure.EnableProtocol;
import io.allenshi.ghaze.autoconfigure.Protocol;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class ProtocolBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationMetadata
                .getAnnotationAttributes(EnableProtocol.class.getName()));
        String name = (String) annotationAttributes.get("name");
        int port = (Integer) annotationAttributes.get("port");

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(Protocol.class);
        beanDefinition.setScope(ConfigurableBeanFactory.SCOPE_SINGLETON);
        beanDefinition.getPropertyValues().addPropertyValue("name", name);
        beanDefinition.getPropertyValues().addPropertyValue("port", port);
        beanDefinitionRegistry.registerBeanDefinition("protocol", beanDefinition);


        BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(ProtocolProcessor.class);
        bdb.addPropertyValue("protocol", beanDefinition);
        beanDefinitionRegistry.registerBeanDefinition(ProtocolProcessor.class.getName(), bdb.getBeanDefinition());
    }
}
