package io.allenshi.ghaze.autoconfigure.spring.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class GraceHazeNamespaceHandler extends NamespaceHandlerSupport {


    public void init() {
        registerBeanDefinitionParser("application", new ApplicationBeanDefinitionParser());
    }
}
