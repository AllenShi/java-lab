package io.allenshi.ghaze.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExampleDisposableBean implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        log.info("disposable bean is to be destroyed");

    }
}
