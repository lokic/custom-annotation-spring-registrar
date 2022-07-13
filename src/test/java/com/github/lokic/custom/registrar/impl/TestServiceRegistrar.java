package com.github.lokic.custom.registrar.impl;

import com.github.lokic.custom.registrar.ProxyFactoryBean;
import com.github.lokic.custom.registrar.ProxyRegistrar;
import com.github.lokic.custom.registrar.annotiation.TestService;

import java.lang.annotation.Annotation;

public class TestServiceRegistrar extends ProxyRegistrar {

    @Override
    protected Class<? extends Annotation> getClassAnnotationType() {
        return TestService.class;
    }

    @Override
    protected Class<? extends ProxyFactoryBean> getFactoryBeanType() {
        return TestServiceFactoryBean.class;
    }
}
