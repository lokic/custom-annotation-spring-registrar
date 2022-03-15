package com.github.lokic.custom.registrar;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.Map;

public class CustomAnnotationSelector implements ImportSelector {

    private static final String REGISTRAR_TYPES_ATTRIBUTE_NAME = "registrarTypes";

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attrs = importingClassMetadata.getAnnotationAttributes(EnableCustomAnnotation.class.getName());
        @SuppressWarnings("unchecked")
        Class<? extends InterfaceRegistrar>[] registrarTypes = (Class<? extends InterfaceRegistrar>[]) attrs.get(REGISTRAR_TYPES_ATTRIBUTE_NAME);
        return Arrays.stream(registrarTypes).map(Class::getName).toArray(String[]::new);
    }
}
