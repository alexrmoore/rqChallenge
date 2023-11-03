package com.example.rqchallenge.employees.iEmployeeController.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Employee(int id, String name, int salary, int age) {}
