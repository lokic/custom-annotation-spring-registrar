package com.github.lokic.custom.registrar.impl;

import com.github.lokic.custom.registrar.InterfaceProxy;
import com.github.lokic.custom.registrar.InvocationHandlerProxy;
import com.github.lokic.custom.registrar.service.RealService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

public class TestServiceProxy extends InterfaceProxy implements InvocationHandlerProxy {

    @Autowired
    private RealService realService;

    public TestServiceProxy(@NonNull Class<?> innerClass) {
        super(innerClass);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        return realService.show();
    }

}
