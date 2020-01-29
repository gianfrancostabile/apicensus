package com.accenture.java.apicensus;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(CamelSpringBootRunner.class)
public class ApiCensusApplicationTest {

    @Test
    public void contextLoads() {
        assertTrue(true);
    }
}
