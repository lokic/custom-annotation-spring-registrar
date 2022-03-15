package com.github.lokic.custom.registrar;

import com.github.lokic.custom.registrar.impl.TestServiceRegistrar;
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
@EnableCustomAnnotation(registrarTypes = TestServiceRegistrar.class)
@RunWith(SpringRunner.class)
public class InterfaceFactoryRegistrarTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserService userService;

    @Test
    public void test() {
        Assertions.assertThat(context.getBeansOfType(UserService.class)).hasSize(1);
        Assert.assertEquals("OK", userService.getName(123L));
    }

}