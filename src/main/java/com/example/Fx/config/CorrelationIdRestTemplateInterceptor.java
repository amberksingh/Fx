package com.example.Fx.config;

import com.example.Fx.util.CorrelationConstants;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class CorrelationIdRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        String correlationId = MDC.get(CorrelationConstants.MDC_KEY);

        if (correlationId != null && !correlationId.isBlank()) {
            request.getHeaders().add(CorrelationConstants.CORRELATION_ID_HEADER, correlationId);
        }

        return execution.execute(request, body);
    }
}