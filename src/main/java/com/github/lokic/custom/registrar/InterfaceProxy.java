package com.github.lokic.custom.registrar;

import lombok.NonNull;
import org.springframework.beans.factory.FactoryBean;

public abstract class InterfaceProxy implements ObjectFactory, FactoryBean<Object> {

    private final Class<?> innerClass;

    public InterfaceProxy(@NonNull Class<?> innerClass) {
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
