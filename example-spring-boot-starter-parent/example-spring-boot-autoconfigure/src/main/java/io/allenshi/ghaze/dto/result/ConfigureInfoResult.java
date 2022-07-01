package io.allenshi.ghaze.dto.result;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 配置信息
 *
 */
@Data
@Builder
public class ConfigureInfoResult implements Serializable {

    private String host;
    private Integer port;
}
