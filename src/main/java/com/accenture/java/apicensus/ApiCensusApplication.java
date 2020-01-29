package com.accenture.java.apicensus;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * The main class.
 *
 * @author Gian F. S.
 */
@SpringBootApplication
@PropertySource({
    "classpath:application.properties", "classpath:database.properties", "classpath:files.properties",
    "classpath:jwt.properties", "classpath:activemq.properties"
})
@CrossOrigin(origins = { "*" })
public class ApiCensusApplication {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        SpringApplication.run(ApiCensusApplication.class, args);
    }
}
