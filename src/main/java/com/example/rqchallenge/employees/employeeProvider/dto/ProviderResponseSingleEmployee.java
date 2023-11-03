package com.example.rqchallenge.employees.employeeProvider.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProviderResponseSingleEmployee(String status, FetchedEmployee data, String message) {}
