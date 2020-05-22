package com.bluesgao.literpc.registry.zk;

public enum ZkNodeType {

    AVAILABLE_SERVER("server"),
    UNAVAILABLE_SERVER("unavailableServer"),
    CLIENT("client");

    private String value;

    private ZkNodeType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
