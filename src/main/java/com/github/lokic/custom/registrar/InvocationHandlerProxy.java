package com.github.lokic.custom.registrar;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

public interface InvocationHandlerProxy extends ObjectFactory, InvocationHandler {

    default Object getObject(Class<?> clazz) {
        if (clazz.isInterface()) {
            ClassLoader classLoader = clazz.getClassLoader();
            Class<?>[] interfaces = new Class<?>[]{clazz};
            return Proxy.newProxyInstance(classLoader, interfaces, this);
        } else {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(clazz);
            enhancer.setCallback(this);
            return enhancer.create();
        }
    }
}
