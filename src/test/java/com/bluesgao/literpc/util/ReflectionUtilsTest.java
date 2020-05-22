package com.bluesgao.literpc.util;

public class ReflectionUtilsTest {

    @org.junit.Test
    public void forName() {
    }

    @org.junit.Test
    public void forName1() {
    }

    @org.junit.Test
    public void forName2() {
    }

    @org.junit.Test
    public void newInstance() {
        try {
            Object obj = ReflectionUtils.newInstance(User.class);
            System.out.println("obj:"+obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void isCanInstance() {
    }
}