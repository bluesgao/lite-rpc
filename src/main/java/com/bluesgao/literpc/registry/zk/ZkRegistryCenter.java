package com.bluesgao.literpc.registry.zk;

import com.bluesgao.literpc.config.ConsumerConfig;
import com.bluesgao.literpc.config.ProviderConfig;
import com.bluesgao.literpc.config.RegistryConfig;
import com.bluesgao.literpc.registry.RegistryCenter;
import com.bluesgao.literpc.registry.ServiceNotifyListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Data
public class ZkRegistryCenter implements RegistryCenter {
    private ZkClient zkClient;
    private final ReentrantLock clientLock = new ReentrantLock();
    private final ReentrantLock serverLock = new ReentrantLock();
    private ConcurrentHashMap<String, IZkChildListener> serviceListeners = new ConcurrentHashMap<>();

    public ZkRegistryCenter(RegistryConfig registryConfig) {
        this.zkClient = new ZkClient(registryConfig.getAddress(), registryConfig.getTimeout(), registryConfig.getConnectTimeout());
        IZkStateListener zkStateListener = new IZkStateListener() {
            @Override
            public void handleStateChanged(Watcher.Event.KeeperState keeperState) throws Exception {
                //todo
                log.info("zkRegistry handleStateChanged notify.");

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
        try {
            clientLock.lock();
            String serviceName = consumerConfig.getIface();
            IZkChildListener zkChildListener = serviceListeners.get(serviceName);
            if (zkChildListener == null) {
                serviceListeners.putIfAbsent(consumerConfig.getIface(), new IZkChildListener() {
                    @Override
                    public void handleChildChange(String parentPath, List<String> currentChilds) {
                        log.info(String.format("[ZookeeperRegistry] service list change: path=%s, currentChilds=%s", parentPath, currentChilds.toString()));
                        //修改服务提供者列表
                        System.out.println("路径："+parentPath);
                        System.out.println("变更的节点为:"+currentChilds);
                    }
                });
                zkChildListener = serviceListeners.get(serviceName);
            }
            //给服务节点添加数据变更监听器
            zkClient.subscribeChildChanges(serviceName, zkChildListener);

            //注册消费者
            String consumers = ZkNodeConvert.consumersPath(consumerConfig);
            String consumerNode = ZkNodeConvert.consumerNodePath(consumerConfig);

            // 先删除节点再创建节点，防止旧节点未正常注销
            if (zkClient.exists(consumerNode)) {
                zkClient.delete(consumerNode);
            }

            if (!zkClient.exists(consumers)) {
                zkClient.createPersistent(consumers, true);
            }
            zkClient.createEphemeral(consumerNode);

        } catch (Throwable e) {
            //throw new Exception(String.format("Failed to register %s to zookeeper(%s), cause: %s", url, null, e.getMessage()), e);
        } finally {
            clientLock.unlock();
        }
        log.info("[ZookeeperRegistry] subscribe service: path=%s, info=%s");

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
            serverLock.lock();

            String providers = ZkNodeConvert.providersPath(providerConfig);
            String providerNode = ZkNodeConvert.providerNodePath(providerConfig);

            // 先删除节点再创建节点，防止旧节点未正常注销
            //存在service/providers/ip:port节点时，删除该provider节点
            if (zkClient.exists(providerNode)) {
                zkClient.delete(providerNode);
            }

            // 2,创建serviceName/providers节点
            if (!zkClient.exists(providers)) {
                zkClient.createPersistent(providers, true);
            }
            // 3,创建serviceName/providers/127.0.0.1:8080节点
            zkClient.createEphemeral(providerNode);

        } catch (Throwable e) {
            //throw new Exception(String.format("Failed to register %s to zookeeper(%s), cause: %s", url, null, e.getMessage()), e);
        } finally {
            serverLock.unlock();
        }
    }

    @Override
    public void unregister(ProviderConfig providerConfig) {
        try {
            String providerNode = ZkNodeConvert.providerNodePath(providerConfig);

            serverLock.lock();

            //存在service/providers/ip:port节点时，删除该provider节点
            if (zkClient.exists(providerNode)) {
                zkClient.delete(providerNode);
            }
        } catch (Throwable e) {
            //throw new Exception(String.format("Failed to unregister %s to zookeeper(%s), cause: %s", url, null, e.getMessage()), e);
        } finally {
            serverLock.unlock();
        }
    }
}
