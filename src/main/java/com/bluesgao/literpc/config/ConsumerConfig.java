package com.bluesgao.literpc.config;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ConsumerConfig implements Serializable {
    private ServerConfig serverConfig;
    private String id;
    private String iface;
    private String protocol;
    private String alias;
    private Integer timeout;
    private Integer retries;
}
