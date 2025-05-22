package com.devtribe.integration.config;

import com.devtribe.domain.post.api.PostController;
import com.devtribe.global.security.CustomAuthenticationFilter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.AliasFor;

/**
 * Spring MVC 컨트롤러 테스트를 위한 커스텀 어노테이션.
 * 이 어노테이션은 @WebMvcTest의 기능을 확장하여 보안 관련 설정을 제외합니다.
 * CustomAuthenticationFilter를 제외하고 SecurityAutoConfiguration을 비활성화합니다.
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest(
    controllers = PostController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CustomAuthenticationFilter.class)
)
public @interface NoSecurityWebMvcTest {

    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
    Class<?>[] controllers() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "value")
    Class<?>[] value() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "includeFilters")
    ComponentScan.Filter[] includeFilters() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "properties")
    String[] properties() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "useDefaultFilters")
    boolean useDefaultFilters() default true;
}
