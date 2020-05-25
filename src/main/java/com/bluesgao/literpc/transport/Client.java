package com.bluesgao.literpc.transport;

import java.net.InetSocketAddress;

public interface Client {
    void reconnect();

    void shutdown();



    boolean isOpen();

    InetSocketAddress getRemoteAddress();

    InetSocketAddress getLocalAddress();

}
