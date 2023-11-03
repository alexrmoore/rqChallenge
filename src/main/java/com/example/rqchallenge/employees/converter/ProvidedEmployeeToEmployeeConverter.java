package com.example.rqchallenge.employees.converter;

import com.example.rqchallenge.employees.employeeProvider.dto.FetchedEmployee;
import com.example.rqchallenge.employees.iEmployeeController.dto.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;


/**
 * A converter to convert between the 'FetchedEmployee' backend DTO, and the 'Employee' DTO.
 * Ideally would've had a domain object between these stages to avoid passing around DTOs,
 * but this seemed redundant considering the scale of this mini-application.
 **/
public class ProvidedEmployeeToEmployeeConverter implements Converter<FetchedEmployee, Employee> {

    private static Logger LOGGER = LoggerFactory.getLogger(ProvidedEmployeeToEmployeeConverter.class);

    @Override
    public Employee convert(FetchedEmployee fetchedEmployee) {
        LOGGER.debug("Converted between ProvidedEmployee and Employee.");
        return new Employee(fetchedEmployee.id(), fetchedEmployee.employee_name(), fetchedEmployee.employee_salary(), fetchedEmployee.employee_age());
    }
}
