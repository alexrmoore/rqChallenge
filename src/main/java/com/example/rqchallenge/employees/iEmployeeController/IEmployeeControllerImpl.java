package com.example.rqchallenge.employees.iEmployeeController;

import com.example.rqchallenge.employees.EmployeeService;
import com.example.rqchallenge.employees.iEmployeeController.dto.Employee;
import com.example.rqchallenge.employees.IEmployeeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/employee")
public class IEmployeeControllerImpl implements IEmployeeController {

    private static Logger LOGGER = LoggerFactory.getLogger(IEmployeeControllerImpl.class);

    private EmployeeService employeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            List<Employee> employees = employeeService.getEmployees();
            LOGGER.debug("Fetched {} employees.", employees.size());
            HttpStatus status = employees.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
            return new ResponseEntity<>(employees, responseHeaders, status);
        } catch (Exception e) {
            LOGGER.error("Error fetching employees.", e);
            return new ResponseEntity<>(Collections.emptyList(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) {
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            List<Employee> namedEmployees = employeeService.getEmployeesByName(searchString);
            LOGGER.debug("Fetched {} employees matching search string {}.", namedEmployees.size(), searchString);
            HttpStatus status = namedEmployees.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
            return new ResponseEntity<>(namedEmployees, responseHeaders, status);
        } catch (Exception e) {
            LOGGER.error("Error fetching employees with search string {}.", searchString, e);
            return new ResponseEntity<>(Collections.emptyList(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee == null) {
                LOGGER.debug("No employee returned with id {}.", id);
                return new ResponseEntity<>(null, responseHeaders, HttpStatus.NO_CONTENT);
            }
            LOGGER.debug("Fetched employees {} by id {}.", employee.name(), id);
            return new ResponseEntity<>(employee, responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error getting employee with id {}.", id, e);
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            Integer highestSalary = employeeService.getHighestSalary();
            LOGGER.debug("Fetched {} as the highest salary of all employees.", highestSalary);
            HttpStatus status = highestSalary.equals(0) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
            return new ResponseEntity<>(highestSalary, responseHeaders, status);
        } catch (Exception e) {
            LOGGER.error("Error getting the highest salary of all employees.", e);
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            List<String> highestEarners = employeeService.getTenHighestEarningEmployeesNames();
            LOGGER.debug("Fetched {} highest earners.", highestEarners.size());
            HttpStatus status = highestEarners.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
            return new ResponseEntity<>(highestEarners, responseHeaders, status);
        } catch (Exception e) {
            LOGGER.error("Error fetching top ten highest earners.", e);
            return new ResponseEntity<>(Collections.emptyList(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> createEmployee(@RequestBody Map<String, Object> employeeInput) {
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            boolean success = employeeService.createEmployee(employeeInput);
            if (success) {
                LOGGER.info("Successfully created employee based on inputs {}", employeeInput);
                return new ResponseEntity<>("Success", responseHeaders, HttpStatus.CREATED);
            }
            LOGGER.info("Failed to create employee: {}.", employeeInput);
            return new ResponseEntity<>("Failed", responseHeaders, HttpStatus.NOT_IMPLEMENTED);
        } catch (Exception e) {
            LOGGER.error("Error creating employee {}.", employeeInput, e);
            return new ResponseEntity<>("Failed", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            String employeeName = employeeService.deleteEmployee(id);
            LOGGER.info("Successfully Deleted employee with id {}.", id);
            return new ResponseEntity<>(employeeName, responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Failed to delete employee with id {}.", id, e);
            return new ResponseEntity<>("", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
