package com.github.lokic.custom.registrar;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;

public abstract class InterfaceRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        Scanner.doScan(getFactoryBeanType(), annotationMetadata, registry, getAnnotationType());
    }

    protected abstract Class<? extends Annotation> getAnnotationType();

    protected abstract Class<? extends InterfaceProxy> getFactoryBeanType();
}
