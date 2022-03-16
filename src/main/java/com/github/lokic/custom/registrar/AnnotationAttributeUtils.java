package com.github.lokic.custom.registrar;

import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AnnotationAttributeUtils {

    public static Map<String, Object> getAnnotationAttributes(AnnotationMetadata importingClassMetadata, Class<? extends Annotation> enableAnnotationType) {
        return Optional.ofNullable(importingClassMetadata.getAnnotationAttributes(enableAnnotationType.getName()))
                .orElseGet(HashMap::new);
    }

}
