package com.bluesgao.literpc.codec;

public interface Codec {
    Object decode(byte[] data, Class clazz);

    Object decode(byte[] data, String className);

    byte[] encode(Object obj);

    byte[] encode(Object obj, String classTypeName);
}
