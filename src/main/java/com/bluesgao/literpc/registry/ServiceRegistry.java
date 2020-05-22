package com.bluesgao.literpc.registry;

import com.bluesgao.literpc.config.ProviderConfig;

public interface ServiceRegistry {
    void register(ProviderConfig providerConfig);
    void unregister(ProviderConfig providerConfig);
}
