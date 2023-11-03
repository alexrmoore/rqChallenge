package com.example.rqchallenge.employees.config;

import com.example.rqchallenge.employees.converter.ProvidedEmployeeToEmployeeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ProvidedEmployeeToEmployeeConverter());
    }
}
