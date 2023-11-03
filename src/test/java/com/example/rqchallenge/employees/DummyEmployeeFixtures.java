package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.employeeProvider.dto.FetchedEmployee;
import com.example.rqchallenge.employees.iEmployeeController.dto.Employee;

import java.util.Map;


/**
 * A utility class of fixtures to de-clutter tests.
 **/
public class DummyEmployeeFixtures {

    private DummyEmployeeFixtures() {
        throw new IllegalStateException("Utility class");
    }

    private static Map<Integer, FetchedEmployee> fetchedEmployeeMap = Map.ofEntries(
            Map.entry(1, new FetchedEmployee(1, "Alex Anderson", 10000, 21, "")),
            Map.entry(2, new FetchedEmployee(2, "Betty Brown", 20000, 22, "")),
            Map.entry(3, new FetchedEmployee(3, "Charlie Charleston", 30000, 23, "")),
            Map.entry(4, new FetchedEmployee(4, "Diana Dott", 40000, 24, "")),
            Map.entry(5, new FetchedEmployee(5, "Ethan Emmerson", 50000, 25, "")),
            Map.entry(6, new FetchedEmployee(6, "Frankie Fairlop", 60000, 26, "")),
            Map.entry(7, new FetchedEmployee(7, "Gregg Greggory", 70000, 27, "")),
            Map.entry(8, new FetchedEmployee(8, "Hannah Hughes", 80000, 28, "")),
            Map.entry(9, new FetchedEmployee(9, "Ian Ingram", 90000, 29, "")),
            Map.entry(10, new FetchedEmployee(10, "Jenna Johnson", 100000, 30, "")),
            Map.entry(11, new FetchedEmployee(11, "Kirk Kellie", 110000, 31, "")),
            Map.entry(12, new FetchedEmployee(12, "Louisa Lemon", 120000, 32, "")),
            Map.entry(13, new FetchedEmployee(13, "Matthew Moore", 130000, 33, ""))
    );

    private static Map<Integer, Employee> employeeMap = Map.ofEntries(
            Map.entry(1, new Employee(1, "Alex Anderson", 10000, 21)),
            Map.entry(2, new Employee(2, "Betty Brown", 20000, 22)),
            Map.entry(3, new Employee(3, "Charlie Charleston", 30000, 23)),
            Map.entry(4, new Employee(4, "Diana Dott", 40000, 24)),
            Map.entry(5, new Employee(5, "Ethan Emmerson", 50000, 25)),
            Map.entry(6, new Employee(6, "Frankie Fairlop", 60000, 26)),
            Map.entry(7, new Employee(7, "Gregg Greggory", 70000, 27)),
            Map.entry(8, new Employee(8, "Hannah Hughes", 80000, 28)),
            Map.entry(9, new Employee(9, "Ian Ingram", 90000, 29)),
            Map.entry(10, new Employee(10, "Jenna Johnson", 100000, 30)),
            Map.entry(11, new Employee(11, "Kirk Kellie", 110000, 31)),
            Map.entry(12, new Employee(12, "Louisa Lemon", 120000, 32)),
            Map.entry(13, new Employee(13, "Matthew Moore", 130000, 33))
    );

    public static Map<Integer, FetchedEmployee> getFetchedEmployeeMap() {
        return fetchedEmployeeMap;
    }

    public static Map<Integer, Employee> getEmployeeMap() {
        return employeeMap;
    }
}
