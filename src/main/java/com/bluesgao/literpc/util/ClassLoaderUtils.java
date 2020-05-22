package com.bluesgao.literpc.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;

public class ClassLoaderUtils {
    public ClassLoaderUtils() {
    }

    public static ClassLoader getCurrentClassLoader() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = ClassLoaderUtils.class.getClassLoader();
        }

        return cl == null ? ClassLoader.getSystemClassLoader() : cl;
    }

    public static ClassLoader getClassLoader(Class<?> clazz) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader != null) {
            return loader;
        } else if (clazz != null) {
            loader = clazz.getClassLoader();
            return loader != null ? loader : clazz.getClassLoader();
        } else {
            return ClassLoader.getSystemClassLoader();
        }
    }
}
