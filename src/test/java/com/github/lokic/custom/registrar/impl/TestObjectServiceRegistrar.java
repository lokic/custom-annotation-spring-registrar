package com.github.lokic.custom.registrar.impl;

import com.github.lokic.custom.registrar.ProxyFactoryBean;
import com.github.lokic.custom.registrar.ProxyRegistrar;
import com.github.lokic.custom.registrar.annotiation.TestObjectService;

import java.lang.annotation.Annotation;

public class TestObjectServiceRegistrar extends ProxyRegistrar {
    @Override
    protected Class<? extends Annotation> getClassAnnotationType() {
        return TestObjectService.class;
    }

    @Override
    protected Class<? extends ProxyFactoryBean> getFactoryBeanType() {
        return TestObjectServiceFactoryBean.class;
    }
}
