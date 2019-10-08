package com.accenture.java.apicensus;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * The main class
 *
 * @author Gian F. S.
 */
@SpringBootApplication
@PropertySource({
	"classpath:application.properties",
	"classpath:database.properties",
	"classpath:files.properties"
})
@ImportResource({ "classpath:context.xml" })
public class ApiCensusApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(ApiCensusApplication.class, args);
	}

}
