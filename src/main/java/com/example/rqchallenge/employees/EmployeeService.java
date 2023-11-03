package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.iEmployeeController.dto.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    List<Employee> getEmployees();

    List<Employee> getEmployeesByName(String searchString);

    Employee getEmployeeById(String id);

    Integer getHighestSalary();

    List<String> getTenHighestEarningEmployeesNames();

    boolean createEmployee(Map<String, Object> employeeInput);

    String deleteEmployee(String id);

}
