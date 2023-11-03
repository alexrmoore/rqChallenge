package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.employeeProvider.dto.PostedEmployee;
import com.example.rqchallenge.employees.iEmployeeController.dto.Employee;


/**
 * A utility class for shared methods.
 **/
public class EmployeeUtilityClass {

    private EmployeeUtilityClass() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean postedAndFetchedEmployeeAreEquivalent(PostedEmployee postedEmployee, Employee fetchedEmployee) {
        return (fetchedEmployee != null) &&
                (postedEmployee.name().equals(fetchedEmployee.name())) &&
                (postedEmployee.salary().equals(String.valueOf(fetchedEmployee.salary()))) &&
                (postedEmployee.age().equals(String.valueOf(fetchedEmployee.age())));
    }

    public static int getHighestSalary(int salary1, int salary2) {
        return salary1 > salary2 ? salary1 : salary2;
    }
}
