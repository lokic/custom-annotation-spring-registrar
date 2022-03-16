package com.github.lokic.custom.registrar.impl;

import com.github.lokic.custom.registrar.MethodInterceptorProxy;
import com.github.lokic.custom.registrar.ProxyFactoryBean;
import lombok.NonNull;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TestObjectServiceFactoryBean extends ProxyFactoryBean implements MethodInterceptorProxy {

    public TestObjectServiceFactoryBean(@NonNull Class<?> innerClass) {
        super(innerClass);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return "Proxy" + MethodInterceptorProxy.super.intercept(o, method, objects, methodProxy);
    }
}
