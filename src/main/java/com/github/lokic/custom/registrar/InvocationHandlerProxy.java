package com.github.lokic.custom.registrar;

import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

public interface InvocationHandlerProxy extends ProxyFactory, InvocationHandler {

    default Object getObject(Class<?> clazz) {
        if (!clazz.isInterface()) {
            throw new IllegalStateException("only support interface");
        }
        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] interfaces = new Class<?>[]{clazz};
        return Proxy.newProxyInstance(classLoader, interfaces, this);
    }
}
