package com.bluesgao.literpc.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bluesgao.literpc.util.ReflectionUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class JsonCodec implements Codec {

    public Object decode(byte[] data, Class clazz) {
        return JSON.parseObject(data, clazz);
    }

    public Object decode(byte[] data, String className) {
        try {
            return this.decode(data, ReflectionUtils.forName(className));
        } catch (ClassNotFoundException e) {
            log.error("decode error", e);
            return null;
        }
    }

    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj, new SerializerFeature[0]);
    }

    public byte[] encode(Object obj, String classTypeName) {
        return encode(obj);
    }
}
