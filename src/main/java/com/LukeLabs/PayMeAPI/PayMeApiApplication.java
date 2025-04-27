package com.LukeLabs.PayMeAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PayMeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayMeApiApplication.class, args);
	}
}
