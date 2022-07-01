package io.allenshi.ghaze.service;

import io.allenshi.ghaze.dto.result.ConfigureInfoResult;

/**
 * EmailService
 *
 */
public interface EmailService {
    /**
     * 获取配置信息
     *
     * @return {@link ConfigureInfoResult}
     */
    ConfigureInfoResult configInfo();
}
