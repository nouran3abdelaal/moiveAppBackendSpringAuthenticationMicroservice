package com.example.AuthenticationMicroservice.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DemoControllerTest {
    @Test
    @DisplayName("Testing the printed value of the demo controller")
    public void testSayHello() {
        DemoController demoController = new DemoController();
        ResponseEntity<String> responseEntity = demoController.sayHello();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Hello from secured endpoint", responseEntity.getBody());
    }

}