package com.bluesgao.literpc.config;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ProviderConfig implements Serializable {
    private ServerConfig serverConfig;
    private String id;
    private String iface;
    private String ref;
    private String server;
    private String alias;
}
