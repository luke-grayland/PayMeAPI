package com.LukeLabs.PayMeAPI;

import com.LukeLabs.PayMeAPI.constants.SwaggerConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@OpenAPIDefinition(
		info = @Info(
				title = "PayMe API",
				version = "1.0.0",
				description = "Offers functionality to issue and manage payment cards along with their associated " +
						"KYC-verified user profiles. Includes capabilities to retrieve card transactions and store, " +
						"access, and analyze spending data. SafeBet mechanism evaluates spend data from the past 24 " +
						"hours to decide if a card should be temporarily blocked, based on total spend, frequency, " +
						"and individual transaction size relative to average monthly income."
		),
		tags = {
				@Tag(name = SwaggerConstants.Tags.HealthCheck, description = "Confirm health status of API"),
				@Tag(name = SwaggerConstants.Tags.Cards, description = "Create and manage cards"),
				@Tag(name = SwaggerConstants.Tags.Transactions, description = "Find all file based transaction associated to a card"),
				@Tag(name = SwaggerConstants.Tags.Spend, description = "Manage spend occurrences")
		},
		servers = {@Server(url = "/", description = "Default Server URL")}
)
@SpringBootApplication
@EnableAsync
public class PayMeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayMeApiApplication.class, args);
	}
}
