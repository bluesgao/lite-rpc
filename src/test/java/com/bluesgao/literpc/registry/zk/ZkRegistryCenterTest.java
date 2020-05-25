package com.bluesgao.literpc.registry.zk;

import com.bluesgao.literpc.config.ProviderConfig;
import com.bluesgao.literpc.config.RegistryConfig;
import org.junit.Before;
import org.junit.Test;

public class ZkRegistryCenterTest {
    private ZkRegistryCenter zkRegistryCenter;
    @Before
    public void setup(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("47.97.205.190:2181");
        registryConfig.setTimeout(3000);
        registryConfig.setConnectTimeout(3000);
        zkRegistryCenter = new ZkRegistryCenter(registryConfig);
    }

    @Test
    public void subscribe() {
    }

    @Test
    public void unsubscribe() {
    }

    @Test
    public void discover() {
    }

    @Test
    public void register() {
        ProviderConfig providerConfig = new ProviderConfig();
        zkRegistryCenter.register(providerConfig);
    }

    @Test
    public void unregister() {
    }
}