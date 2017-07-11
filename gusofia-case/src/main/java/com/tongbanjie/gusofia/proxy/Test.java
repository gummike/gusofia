package com.tongbanjie.gusofia.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zi.you
 * @date 17/6/20
 */
public class Test {

    public static void main(String[] args) {
        Test test = new Test();
        Itf proxyObject = test.getProxyObject();
        proxyObject.getName();
        proxyObject.getGender();
    }

    public  Itf getProxyObject(){
        final Itf itf = new Stub();
        return  (Itf) Proxy.newProxyInstance(Test.class.getClassLoader(), itf.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("getName")) {
                    System.out.println("获取名字前");
                }
                method.invoke(itf, args);
                if (method.getName().equals("getName")) {
                    System.out.println("获取名字后");
                }
                return proxy;
            }
        });
    }
}
