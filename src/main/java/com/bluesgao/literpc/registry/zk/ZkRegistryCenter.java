package com.bluesgao.literpc.registry.zk;

import com.bluesgao.literpc.config.ConsumerConfig;
import com.bluesgao.literpc.config.ProviderConfig;
import com.bluesgao.literpc.config.RegistryConfig;
import com.bluesgao.literpc.registry.RegistryCenter;
import com.bluesgao.literpc.registry.ServiceNotifyListener;
import com.bluesgao.literpc.registry.URL;
import com.bluesgao.literpc.util.Constants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Data
public class ZkRegistryCenter implements RegistryCenter {
    private ZkClient zkClient;
    private final ReentrantLock clientLock = new ReentrantLock();
    private final ReentrantLock serverLock = new ReentrantLock();
    private Set<URL> availableServices = new ConcurrentSkipListSet<>();
    private ConcurrentHashMap<URL, ConcurrentHashMap<ServiceNotifyListener, IZkChildListener>> serviceListeners = new ConcurrentHashMap<>();

    public ZkRegistryCenter(RegistryConfig registryConfig) {
        this.zkClient = new ZkClient(registryConfig.getAddress(), registryConfig.getTimeout(), registryConfig.getConnectTimeout());
        IZkStateListener zkStateListener = new IZkStateListener() {
            @Override
            public void handleStateChanged(Watcher.Event.KeeperState keeperState) throws Exception {
                //todo
            }

            @Override
            public void handleNewSession() throws Exception {
                log.info("zkRegistry get new session notify.");
                //todo 重连
            }

            @Override
            public void handleSessionEstablishmentError(Throwable throwable) throws Exception {
                log.info("zkRegistry get SessionEstablishmentError notify.");

            }
        };
        zkClient.subscribeStateChanges(zkStateListener);
    }

    @Override
    public void subscribe(ConsumerConfig consumerConfig, ServiceNotifyListener listener) {
        //服务消费者订阅
    }

    @Override
    public void unsubscribe(ConsumerConfig consumerConfig, ServiceNotifyListener listener) {

    }

    @Override
    public List<ConsumerConfig> discover(ConsumerConfig consumerConfig) {
        return null;
    }

    @Override
    public void register(ProviderConfig providerConfig) {
        //服务提供者注册
        try {
            URL url = new URL();//todo 将provider转为url
            serverLock.lock();
            // 防止旧节点未正常注销
            removeNode(url, ZkNodeType.AVAILABLE_SERVER);
            removeNode(url, ZkNodeType.UNAVAILABLE_SERVER);
            createNode(url, ZkNodeType.UNAVAILABLE_SERVER);
        } catch (Throwable e) {
            throw new Exception(String.format("Failed to register %s to zookeeper(%s), cause: %s", url, getUrl(), e.getMessage()), e);
        } finally {
            serverLock.unlock();
        }
    }

    @Override
    public void unregister(ProviderConfig providerConfig) {

    }

    private void createNode(URL url, ZkNodeType nodeType) {
        String nodeTypePath = "";
        if (!zkClient.exists(nodeTypePath)) {
            zkClient.createPersistent(nodeTypePath, true);
        }
        zkClient.createEphemeral("");
    }

    private void removeNode(URL url, ZkNodeType nodeType) {
        String nodePath = "todo";
        if (zkClient.exists(nodePath)) {
            zkClient.delete(nodePath);
        }
    }
}
