package com.bluesgao.literpc.config;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class RegistryConfig implements Serializable {
    private String protocol = "jsfRegistry";
    private String address;
    private String index = "i.jsf.jd.com";
    private boolean register = true;
    private boolean subscribe = true;
    private int timeout = 5000;
    private int connectTimeout = 20000;
    private String file;
    private int batchCheck = 10;
    protected Map<String, String> parameters;
}
