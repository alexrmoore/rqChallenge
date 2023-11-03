package com.example.rqchallenge.employees.config;

import com.example.rqchallenge.employees.EmployeeProvider;
import com.example.rqchallenge.employees.employeeProvider.RestEmployeeProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class EmployeeProviderConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public EmployeeProvider restEmployeeProvider(@Value("${rq_challenge.employee_source.base_url}") String baseUrl,
                                                 RestTemplate restTemplate) {
        return new RestEmployeeProvider(baseUrl, restTemplate);
    }
}
