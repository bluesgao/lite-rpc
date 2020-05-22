package com.bluesgao.literpc.protocol;

import io.netty.buffer.ByteBuf;

public interface Protocol {
    Object decode(ByteBuf var1, Class var2);
    Object decode(ByteBuf var1, String var2);
    ByteBuf encode(Object var1, ByteBuf var2);
}
