package com.bluesgao.literpc.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;

public class ReflectionUtils {
    private static Set<Class> primitiveSet = new HashSet();

    public ReflectionUtils() {
    }

    public static Class forName(String className) throws ClassNotFoundException {
        return forName(className, true);
    }

    public static Class forName(String className, boolean initialize) throws ClassNotFoundException {
        return Class.forName(className, initialize, ClassLoaderUtils.getCurrentClassLoader());
    }

    public static Class forName(String className, ClassLoader cl) throws ClassNotFoundException {
        return Class.forName(className, true, cl);
    }

    public static <T> T newInstance(Class clazz) throws Exception {
        if (primitiveSet.contains(clazz)) {
            return null;
        } else if (!isCanInstance(clazz)) {
            return null;
        } else {
            Constructor defaultConstructor = null;
            Object instance;
            if (clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())) {
                Constructor[] constructors = clazz.getDeclaredConstructors();
                for (Constructor c : constructors) {
                    if (c.getParameterTypes().length == 1) {
                        defaultConstructor = c;
                        break;
                    }
                }

                if (defaultConstructor != null) {
                    if (defaultConstructor.isAccessible()) {
                        instance = defaultConstructor.newInstance(null);
                        return (T)instance;
                    } else {
                        try {
                            defaultConstructor.setAccessible(true);
                            instance = defaultConstructor.newInstance(null);
                        } finally {
                            defaultConstructor.setAccessible(false);
                        }

                        return (T)instance;
                    }
                } else {
                    throw new Exception("The " + clazz.getCanonicalName() + " has no default constructor!");
                }
            } else {
                try {
                    return (T)clazz.newInstance();
                } catch (Exception e) {
                    defaultConstructor = clazz.getDeclaredConstructor();
                    if (defaultConstructor.isAccessible()) {
                        throw new Exception("The " + clazz.getCanonicalName() + " has no default constructor!", e);
                    } else {
                        try {
                            defaultConstructor.setAccessible(true);
                            instance = defaultConstructor.newInstance();
                        } finally {
                            defaultConstructor.setAccessible(false);
                        }

                        return (T)instance;
                    }
                }
            }
        }
    }

    public static boolean isCanInstance(Class clazz) {
        return !clazz.isArray() && !clazz.isEnum();
    }

    static {
        primitiveSet.add(Integer.class);
        primitiveSet.add(Long.class);
        primitiveSet.add(Float.class);
        primitiveSet.add(Byte.class);
        primitiveSet.add(Short.class);
        primitiveSet.add(Double.class);
        primitiveSet.add(Character.class);
        primitiveSet.add(Boolean.class);
        primitiveSet.add((new HashMap()).keySet().getClass());
        primitiveSet.add((new TreeMap()).values().getClass());
        primitiveSet.add((new ArrayList()).subList(0, 0).getClass());
    }
}
