package com.quxinjun.email.service;

import com.quxinjun.email.hello.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloTest {

    @Autowired
    HelloService helloService;

    @Test
    public void sayHelloTest(){
        helloService.sayHello();
    }

}
