package com.LukeLabs.PayMeAPI;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@OpenAPIDefinition(
		info = @Info(
				title = "PayMe API",
				version = "1.0.0",
				description = "Provides functionality to issue and manage cards, retrieve related transactions, and view associated KYC profiles."
		)
)
@SpringBootApplication
@EnableAsync
public class PayMeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayMeApiApplication.class, args);
	}
}
