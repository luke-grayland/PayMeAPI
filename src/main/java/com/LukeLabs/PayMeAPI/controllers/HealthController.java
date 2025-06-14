package com.LukeLabs.PayMeAPI.controllers;

import com.LukeLabs.PayMeAPI.constants.SwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    @Operation(summary = "Confirm health status of API", tags = { SwaggerConstants.Tags.HealthCheck })
    public String health() {
        return "I'm alive!";
    }
}
