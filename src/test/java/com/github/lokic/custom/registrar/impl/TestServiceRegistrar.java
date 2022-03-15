package com.github.lokic.custom.registrar.impl;

import com.github.lokic.custom.registrar.InterfaceFactory;
import com.github.lokic.custom.registrar.InterfaceRegistrar;
import com.github.lokic.custom.registrar.annotiation.TestService;

import java.lang.annotation.Annotation;

public class TestServiceRegistrar extends InterfaceRegistrar {

    @Override
    protected Class<? extends Annotation> getAnnotationType() {
        return TestService.class;
    }

    @Override
    protected Class<? extends InterfaceFactory> getFactoryBeanType() {
        return TestServiceFactory.class;
    }
}
