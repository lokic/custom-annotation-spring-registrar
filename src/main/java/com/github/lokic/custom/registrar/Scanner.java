package com.github.lokic.custom.registrar;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Scanner extends ClassPathBeanDefinitionScanner {

    private static final String DEFAULT_BASE_PACKAGES_NAME = "basePackages";

    private final Class<? extends InterfaceProxy> factoryBeanType;

    @SafeVarargs
    public static Scanner doScan(Class<? extends InterfaceProxy> factoryBean, AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry, Class<? extends Annotation>... customIncludeFilters) {
        Scanner scanner = new Scanner(factoryBean, registry) {
            @Override
            List<Class<? extends Annotation>> getCustomIncludeFilters() {
                return Arrays.stream(customIncludeFilters).collect(Collectors.toList());
            }
        };
        Set<String> basePackages = getBasePackages(annotationMetadata);
        scanner.doScan(basePackages.toArray(new String[0]));
        return scanner;
    }


    Scanner(Class<? extends InterfaceProxy> factoryBeanType, BeanDefinitionRegistry registry) {
        super(registry);
        this.factoryBeanType = factoryBeanType;
    }

    @Override
    protected void registerDefaultFilters() {
        addIncludeFilters(getCustomIncludeFilters());
    }

    abstract List<Class<? extends Annotation>> getCustomIncludeFilters();

    final void addIncludeFilters(List<Class<? extends Annotation>> annotationTypes) {
        for (Class<? extends Annotation> annotationType : annotationTypes) {
            addIncludeFilter(new AnnotationTypeFilter(annotationType));
        }
    }


    @SneakyThrows
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        for (BeanDefinitionHolder holder : beanDefinitions) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            definition.getConstructorArgumentValues()
                    .addIndexedArgumentValue(0, Class.forName(definition.getBeanClassName()));
            definition.setBeanClass(factoryBeanType);
        }
        return beanDefinitions;
    }

    @Override
    public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() &&
                getCustomIncludeFilters().stream().anyMatch(clazz -> beanDefinition.getMetadata().hasAnnotation(clazz.getName()));
    }


    /**
     * 获取basePackages
     */
    private static Set<String> getBasePackages(AnnotationMetadata annotationMetadata) {
        Map<String, Object> attributes = getAnnotationAttributes(annotationMetadata);
        if (attributes.containsKey(DEFAULT_BASE_PACKAGES_NAME)) {
            Object basePackages = attributes.get(DEFAULT_BASE_PACKAGES_NAME);
            if (basePackages.getClass() == String[].class) {
                return Arrays.stream((String[]) basePackages).collect(Collectors.toSet());
            }
        }
        Set<String> basePackages = new HashSet<>();
        basePackages.add(
                ClassUtils.getPackageName(annotationMetadata.getClassName()));
        return basePackages;
    }

    private static Map<String, Object> getAnnotationAttributes(AnnotationMetadata importingClassMetadata) {
        return Optional.ofNullable(importingClassMetadata.getAnnotationAttributes(importingClassMetadata.getClassName()))
                .orElseGet(HashMap::new);
    }


}

