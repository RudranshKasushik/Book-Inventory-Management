package com.cpg.bim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BookInventoryManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookInventoryManagementApplication.class, args);
	}

	@Bean
	public RestTemplate restTempplate() {
		return new RestTemplate();
	}
}
