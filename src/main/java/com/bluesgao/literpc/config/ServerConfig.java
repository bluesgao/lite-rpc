package com.bluesgao.literpc.config;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServerConfig implements Serializable {
    private String ip;
    private Integer prot;
    private String serverName;
}