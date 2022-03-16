package com.github.lokic.custom.registrar;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 默认的EnableXxx的Annotation，适合本地项目中快捷使用custom annotation的场景。
 * <p>建议使用自定义EnableXxx，能更加便捷得扩展二方包或者三方包。
 * <p>Note: 如果使用自定义EnableXxx，需要覆盖 {@link ProxyRegistrar#getEnableAnnotationType}
 * <p>自定义EnableXxx参考实现如下：
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
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(CustomAnnotationSelector.class)
public @interface EnableCustomAnnotation {

    Class<? extends ProxyRegistrar>[] registrarTypes();

    String[] basePackages() default {};
}
