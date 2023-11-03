package com.example.rqchallenge.employees.config;

import com.example.rqchallenge.employees.EmployeeProvider;
import com.example.rqchallenge.employees.employeeService.BasicEmployeeServiceImpl;
import com.example.rqchallenge.employees.converter.ProvidedEmployeeToEmployeeConverter;
import com.example.rqchallenge.employees.employeeService.CachedEmployeeServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
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
