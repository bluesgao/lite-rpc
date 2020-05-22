package com.bluesgao.literpc.registry;

import java.util.List;


/**
 * Notify when service changed.
 */

public interface ServiceNotifyListener {

    void notify(URL registryURL, List<URL> URLS);
}
