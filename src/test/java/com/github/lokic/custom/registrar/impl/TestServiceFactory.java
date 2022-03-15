package com.github.lokic.custom.registrar.impl;

import com.github.lokic.custom.registrar.InterfaceFactory;
import com.github.lokic.custom.registrar.InvocationHandlerProxy;
import com.github.lokic.custom.registrar.service.RealService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

public class TestServiceFactory extends InterfaceFactory implements InvocationHandlerProxy {

    @Autowired
    private RealService realService;

    public TestServiceFactory(@NonNull Class<?> innerClass) {
        super(innerClass);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        return realService.show();
    }

}
