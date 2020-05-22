package com.bluesgao.literpc.registry;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class URL implements Serializable {
    private String ip;
    private int port;
    private int pid;
    private String iface;
    private String alias;
    private int protocol;
    private Map<String, String> attrs;
    private int timeout;
    private boolean random;
    private long stTime;
    private String insKey;
    private long dataVersion;
}
