package com.accenture.java.apicensus;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiCensusApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(ApiCensusApplication.class, args);
	}

}
