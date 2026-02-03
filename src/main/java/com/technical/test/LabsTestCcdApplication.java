package com.technical.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LabsTestCcdApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabsTestCcdApplication.class, args);
	}

}
