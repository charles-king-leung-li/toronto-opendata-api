package com.toronto.opendata.dataportal.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class WebConfigTests {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testRestTemplateCreation() {
        assertNotNull(restTemplate, "RestTemplate should be created");
    }
}