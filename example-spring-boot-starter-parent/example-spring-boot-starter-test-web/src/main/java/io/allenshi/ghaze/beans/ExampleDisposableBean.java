package io.allenshi.ghaze.beans;

import org.springframework.beans.factory.DisposableBean;

public class ExampleDisposableBean implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("disposable bean is to be destroyed");

    }
}
