package com.oplayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OplayerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OplayerApplication.class, args);
	}
}
