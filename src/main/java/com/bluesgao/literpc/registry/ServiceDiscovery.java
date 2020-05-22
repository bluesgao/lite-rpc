package com.bluesgao.literpc.registry;

import com.bluesgao.literpc.config.ConsumerConfig;

import java.util.List;

public interface ServiceDiscovery {
    void subscribe(ConsumerConfig consumerConfig, ServiceNotifyListener listener);
    void unsubscribe(ConsumerConfig consumerConfig, ServiceNotifyListener listener);
    List<ConsumerConfig> discover(ConsumerConfig consumerConfig);
}
