package com.bluesgao.literpc.registry.zk;

import com.bluesgao.literpc.config.ConsumerConfig;
import com.bluesgao.literpc.config.ProviderConfig;
import com.bluesgao.literpc.util.Constants;

public class ZkNodeConvert {
    public static String providersPath(ProviderConfig providerConfig) {
        return Constants.ROOT + Constants.PATH_SEPARATOR + providerConfig.getIface() + Constants.PATH_SEPARATOR + Constants.PROVIDERS;
    }

    public static String providerNodePath(ProviderConfig providerConfig) {
        return providersPath(providerConfig) + Constants.PATH_SEPARATOR + providerConfig.getServerConfig().getIp() + ":" + providerConfig.getServerConfig().getProt();
    }

    public static String consumersPath(ConsumerConfig consumerConfig) {
        return Constants.ROOT + Constants.PATH_SEPARATOR + consumerConfig.getIface() + Constants.PATH_SEPARATOR + Constants.CONSUMERS;
    }

    public static String consumerNodePath(ConsumerConfig consumerConfig) {
        return consumersPath(consumerConfig) + Constants.PATH_SEPARATOR + consumerConfig.getServerConfig().getIp() + ":" + consumerConfig.getServerConfig().getProt();
    }
}