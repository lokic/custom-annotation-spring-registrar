package com.github.lokic.custom.registrar;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 默认的EnableXxx的Annotation，适合本地项目中快捷使用custom annotation的场景。
 * <p>
 * <p>建议使用自定义EnableXxx，能更加便捷得扩展二方包或者三方包。如
 * <pre class="code">
 * &#064;Retention(RetentionPolicy.RUNTIME)
 * &#064;Target(ElementType.TYPE)
 * &#064;Documented
 * &#064;Import(XxxRegistrar.class)
 * public @interface EnableXxx {
 *     String[] basePackages() default {};
 * }
 * </pre>
 */
@Import(CustomAnnotationSelector.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface EnableCustomAnnotation {

    Class<? extends InterfaceRegistrar>[] registrarTypes();

    String[] basePackages() default {};
}
