package com.github.lokic.custom.registrar;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Scanner extends ClassPathBeanDefinitionScanner {

    private static final String DEFAULT_BASE_PACKAGES_ATTRIBUTE_NAME = "basePackages";

    private final Class<? extends InterfaceFactory> factoryBeanType;

    private final String[] basePackages;


    @SafeVarargs
    public static Scanner doScan(Class<? extends Annotation> enableAnnotationType, Class<? extends InterfaceFactory> factoryBean, AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry, Class<? extends Annotation>... customIncludeFilters) {
        Scanner scanner = new Scanner(enableAnnotationType, factoryBean, registry, annotationMetadata) {
            @Override
            List<Class<? extends Annotation>> getCustomIncludeFilters() {
                return Arrays.stream(customIncludeFilters).collect(Collectors.toList());
            }
        };
        scanner.doScan();
        return scanner;
    }


    Scanner(Class<? extends Annotation> enableAnnotationType, Class<? extends InterfaceFactory> factoryBeanType, BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata) {
        super(registry);
        this.basePackages = getBasePackages(enableAnnotationType, annotationMetadata).toArray(new String[0]);
        this.factoryBeanType = factoryBeanType;
    }

    private Set<BeanDefinitionHolder> doScan() {
        return doScan(basePackages);
    }

    final void addIncludeFilters(List<Class<? extends Annotation>> annotationTypes) {
        for (Class<? extends Annotation> annotationType : annotationTypes) {
            addIncludeFilter(new AnnotationTypeFilter(annotationType));
        }
    }

    @Override
    protected void registerDefaultFilters() {
        addIncludeFilters(getCustomIncludeFilters());
    }

    protected String getBasePackagesAttributeName() {
        return DEFAULT_BASE_PACKAGES_ATTRIBUTE_NAME;
    }

    public Set<BeanDefinition> findCandidateComponents() {
        return Arrays.stream(basePackages)
                .map(this::findCandidateComponents)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    abstract List<Class<? extends Annotation>> getCustomIncludeFilters();

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
    private Set<String> getBasePackages(Class<? extends Annotation> enableAnnotationType, AnnotationMetadata annotationMetadata) {
        if (enableAnnotationType != null) {
            Map<String, Object> attributes = getAnnotationAttributes(enableAnnotationType, annotationMetadata);
            if (attributes.containsKey(getBasePackagesAttributeName())) {
                Object basePackages = attributes.get(getBasePackagesAttributeName());
                if (basePackages.getClass() == String[].class) {
                    Set<String> packages = Arrays.stream((String[]) basePackages).collect(Collectors.toSet());
                    if (!CollectionUtils.isEmpty(packages)) {
                        return packages;
                    }
                }
            }
        }
        Set<String> basePackages = new HashSet<>();
        basePackages.add(
                ClassUtils.getPackageName(annotationMetadata.getClassName()));
        return basePackages;
    }

    private Map<String, Object> getAnnotationAttributes(Class<? extends Annotation> enableAnnotationType, AnnotationMetadata importingClassMetadata) {
        return Optional.ofNullable(importingClassMetadata.getAnnotationAttributes(enableAnnotationType.getName()))
                .orElseGet(HashMap::new);
    }


}

