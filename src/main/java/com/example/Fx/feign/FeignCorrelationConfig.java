package com.example.Fx.feign;

import com.example.Fx.util.CorrelationConstants;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@Slf4j
public class FeignCorrelationConfig {

    @Bean
    public RequestInterceptor correlationIdFeignInterceptor() {
        return requestTemplate -> {
            String correlationId = MDC.get(CorrelationConstants.MDC_KEY);
            log.info("Inside Feign interceptor, MDC correlationId = {}", correlationId);

            if (correlationId == null || correlationId.isBlank()) {
                ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                log.info("Using ServletRequestAttributes to fetch correlationId");
                if (attrs != null) {
                    correlationId = attrs.getRequest().getHeader(CorrelationConstants.CORRELATION_ID_HEADER);
                }
            }

            if (correlationId != null && !correlationId.isBlank()) {
                log.info("Added correlation header to Feign request");
                requestTemplate.header(CorrelationConstants.CORRELATION_ID_HEADER, correlationId);
            } else {
                log.warn("MDC correlationId missing in Feign interceptor");
            }
        };
    }
}