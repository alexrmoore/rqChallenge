package com.example.rqchallenge.employees.employeeProvider.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProviderResponseEmployeeList(String status, FetchedEmployee[] data, String message) {}
