package com.github.lokic.custom.registrar;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public interface MethodInterceptorProxy extends ProxyFactory, MethodInterceptor {

    default Object getObject(Class<?> clazz) {
        if (clazz.isInterface()) {
            throw new IllegalStateException("not support interface");
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    default Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return methodProxy.invokeSuper(o, objects);
    }
}
