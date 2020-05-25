package com.bluesgao.literpc.registry.zk;

import com.bluesgao.literpc.config.RegistryConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class ZkClientTest {
    private ZkClient client;
    @Before
    public void setup(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("127.0.0.1:2181");
        registryConfig.setTimeout(3000);
        registryConfig.setConnectTimeout(3000);
        client = new ZkClient(registryConfig.getAddress());
    }

    @Test
    public void crateNode() {
    }

    @Test
    public void deleteNode() {
    }

    @Test
    public void deleteChildrenIfNeededNode() {
    }

    @Test
    public void isExistNode() {
        boolean f = client.isExistNode("/");
        log.info("isExistNode:{}",f);
    }

    @Test
    public void isPersistentNode() {
    }

    @Test
    public void getNodeData() {
        String ret = client.getNodeData("/");
        log.info("getNodeData:{}",ret);
    }

    @Test
    public void updateNodeData() {
    }

    @Test
    public void startTransaction() {
    }

    @Test
    public void registerWatcherNodeChanged() {
        client.crateNode("/test/providers/myservice",CreateMode.PERSISTENT,"2321");

        client.registerWatcherNodeChanged("/test", new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                log.info("nodeChanged");
            }
        });
    }
}