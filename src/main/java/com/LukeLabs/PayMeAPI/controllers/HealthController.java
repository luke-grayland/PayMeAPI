package com.LukeLabs.PayMeAPI.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @Tag(name = "Health Check", description = "Confirm health status of API")
    @GetMapping("/")
    public String health() {
        return "I'm alive!";
    }
}
