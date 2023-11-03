package com.example.rqchallenge.employees.employeeService;

import com.example.rqchallenge.employees.EmployeeProvider;
import com.example.rqchallenge.employees.EmployeeService;
import com.example.rqchallenge.employees.EmployeeUtilityClass;
import com.example.rqchallenge.employees.employeeProvider.dto.FetchedEmployee;
import com.example.rqchallenge.employees.employeeProvider.dto.PostedEmployee;
import com.example.rqchallenge.employees.iEmployeeController.dto.Employee;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * A base implementation that doesn't use caching.
 * This bean will be enabled when the rq_challenge.employee_service.caching_enabled is set to 'false'.
 **/

@Component
public class BasicEmployeeServiceImpl implements EmployeeService {

    private final EmployeeProvider employeeProvider;

    private final ConversionService conversionService;

    public BasicEmployeeServiceImpl(EmployeeProvider employeeProvider, ConversionService conversionService) {
        this.employeeProvider = employeeProvider;
        this.conversionService = conversionService;
    }

    public List<Employee> getEmployees() {
        return employeeProvider.getEmployees().stream()
                .map(e -> conversionService.convert(e, Employee.class))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesByName(String searchString) {
        return employeeProvider.getEmployees().stream()
                .map(e -> conversionService.convert(e, Employee.class))
                .filter(employee -> employee.name().contains(searchString))
                .collect(Collectors.toList());
    }

    public Employee getEmployeeById(String id) {
        FetchedEmployee fetchedEmployee = employeeProvider.getEmployee(id);
        return conversionService.convert(fetchedEmployee, Employee.class);
    }

    public Integer getHighestSalary() {
        return employeeProvider.getEmployees().stream()
                .map(e -> conversionService.convert(e, Employee.class))
                .map(Employee::salary)
                .reduce(0, (salary1, salary2) -> EmployeeUtilityClass.getHighestSalary(salary1, salary2));
    }

    public List<String> getTenHighestEarningEmployeesNames() {
        return employeeProvider.getEmployees().stream()
                .map(e -> conversionService.convert(e, Employee.class))
                .sorted(Comparator.comparing(Employee::salary).reversed())
                .map(Employee::name)
                .limit(10)
                .collect(Collectors.toList());
    }

    public boolean createEmployee(Map<String, Object> employeeInput) {
        PostedEmployee postedEmployee = new PostedEmployee(
                String.valueOf(employeeInput.get("name")),
                String.valueOf(employeeInput.get("salary")),
                String.valueOf(employeeInput.get("age")));
        Employee fetchedEmployee = conversionService.convert(employeeProvider.createEmployee(postedEmployee), Employee.class);
        return EmployeeUtilityClass.postedAndFetchedEmployeeAreEquivalent(postedEmployee, fetchedEmployee);
    }

    public String deleteEmployee(String id) {
        String employeeName = getEmployeeById(id).name();
        employeeProvider.deleteEmployee(id);
        return employeeName;
    }
}
