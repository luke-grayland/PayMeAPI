package com.LukeLabs.PayMeAPI.utilities.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {
    @Value("${paymeapi.logging.maxPayloadLength}")
    private String maxPayloadLength;

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
                = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(Integer.parseInt(maxPayloadLength));
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("Inbound request: ");
        return filter;
    }
}
