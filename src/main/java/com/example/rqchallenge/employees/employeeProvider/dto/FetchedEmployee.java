package com.example.rqchallenge.employees.employeeProvider.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FetchedEmployee(int id, String employee_name, int employee_salary, int employee_age, String profile_image) {}
