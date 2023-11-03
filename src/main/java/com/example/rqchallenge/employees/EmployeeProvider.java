package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.employeeProvider.dto.FetchedEmployee;
import com.example.rqchallenge.employees.employeeProvider.dto.PostedEmployee;

import java.util.List;

/**
 * Used an interface here to allow for new 'employee sources' in the future.
 **/
public interface EmployeeProvider {

    List<FetchedEmployee> getEmployees();

    FetchedEmployee getEmployee(String id);

    FetchedEmployee createEmployee(PostedEmployee postedEmployee);

    void deleteEmployee(String id);


}
