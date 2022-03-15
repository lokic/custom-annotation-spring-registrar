package com.github.lokic.custom.registrar;

import lombok.NonNull;
import org.springframework.beans.factory.FactoryBean;

public abstract class InterfaceFactory implements ProxyFactory, FactoryBean<Object> {

    private final Class<?> innerClass;

    public InterfaceFactory(@NonNull Class<?> innerClass) {
        this.innerClass = innerClass;
    }

    @Override
    public final Object getObject() throws Exception {
        if (!innerClass.isInterface()) {
            throw new IllegalStateException("only support interface");
        }
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
