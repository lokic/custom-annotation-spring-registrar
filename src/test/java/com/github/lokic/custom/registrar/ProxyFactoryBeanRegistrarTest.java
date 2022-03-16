package com.github.lokic.custom.registrar;

import com.github.lokic.custom.registrar.impl.TestObjectServiceRegistrar;
import com.github.lokic.custom.registrar.impl.TestServiceRegistrar;
import com.github.lokic.custom.registrar.service.UserObjectService;
import com.github.lokic.custom.registrar.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@ComponentScan("com.github.lokic.custom.registrar")
@EnableCustomAnnotation(registrarTypes = {TestServiceRegistrar.class, TestObjectServiceRegistrar.class})
@RunWith(SpringRunner.class)
public class ProxyFactoryBeanRegistrarTest {

    @Autowired
    private ApplicationContext context;


    @Test
    public void test() {
        Assertions.assertThat(context.getBeansOfType(UserService.class)).hasSize(1);

        UserService userService = context.getBean(UserService.class);
        Assert.assertEquals("OK", userService.getName(123L));
    }

    @Test
    public void test_object() {
        Assertions.assertThat(context.getBeansOfType(UserObjectService.class)).hasSize(1);

        UserObjectService userObjectService = context.getBean(UserObjectService.class);
        Assert.assertEquals("ProxyUserObjectService:ok", userObjectService.getName(123L));
    }

}