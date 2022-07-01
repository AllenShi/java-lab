package io.allenshi.ghaze.service.impl;

import io.allenshi.ghaze.dto.result.ConfigureInfoResult;
import io.allenshi.ghaze.service.EmailService;

/**
 * EmailServiceImpl
 */
public class EmailServiceImpl implements EmailService {
    private String host;
    private Integer port;

    public EmailServiceImpl(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public ConfigureInfoResult configInfo() {
        return ConfigureInfoResult.builder().host(host).port(port).build();
    }
}
