package io.allenshi.ghaze.autoconfigure.spring.schema;

import io.allenshi.ghaze.autoconfigure.Application;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class ApplicationBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return Application.class;
    }

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");
        String version = element.getAttribute("version");

        if(StringUtils.hasLength(name)) {
            builder.addPropertyValue("name", name);
        }
        if(StringUtils.hasLength(version)) {
            builder.addPropertyValue("version", version);
        }
    }

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }
}
