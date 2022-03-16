package com.github.lokic.custom.registrar;

import lombok.NonNull;
import org.springframework.beans.factory.FactoryBean;

public abstract class ProxyFactoryBean implements ProxyFactory, FactoryBean<Object> {

    private final Class<?> innerClass;

    public ProxyFactoryBean(@NonNull Class<?> innerClass) {
        this.innerClass = innerClass;
    }

    @Override
    public final Object getObject() throws Exception {
        return getObject(innerClass);
    }

    @Override
    public Class<?> getObjectType() {
        return innerClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
