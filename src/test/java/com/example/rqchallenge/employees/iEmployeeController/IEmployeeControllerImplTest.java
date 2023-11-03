package com.example.rqchallenge.employees.iEmployeeController;

import com.example.rqchallenge.employees.DummyEmployeeFixtures;
import com.example.rqchallenge.employees.EmployeeService;
import com.example.rqchallenge.employees.iEmployeeController.dto.Employee;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IEmployeeControllerImplTest {

    @Mock
    private EmployeeService employeeService;

    private IEmployeeControllerImpl iEmployeeController;

    @BeforeEach
    void setUp() {
        iEmployeeController = new IEmployeeControllerImpl();
        iEmployeeController.setEmployeeService(employeeService);
    }

    @Test
    void getAllEmployeesSuccessfully() {
        when(employeeService.getEmployees()).thenReturn(new ArrayList<>(DummyEmployeeFixtures.getEmployeeMap().values()));
        ResponseEntity<List<Employee>> response = iEmployeeController.getAllEmployees();
        Assertions.assertEquals(13, response.getBody().size());
        assertThat(response.getBody(), is(new ArrayList<>(DummyEmployeeFixtures.getEmployeeMap().values())));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAllEmployeesNoResult() {
        when(employeeService.getEmployees()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Employee>> response = iEmployeeController.getAllEmployees();
        Assertions.assertEquals(0, response.getBody().size());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getAllEmployeesFailed() {
        when(employeeService.getEmployees()).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        ResponseEntity<List<Employee>> response = iEmployeeController.getAllEmployees();
        Assertions.assertEquals(0, response.getBody().size());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getEmployeesByNameSearch() {
        when(employeeService.getEmployeesByName(any())).thenReturn(List.of(DummyEmployeeFixtures.getEmployeeMap().get(4)));
        ResponseEntity<List<Employee>> response = iEmployeeController.getEmployeesByNameSearch("Diana");
        Assertions.assertEquals(1, response.getBody().size());
        assertEquals(response.getBody().get(0), DummyEmployeeFixtures.getEmployeeMap().get(4));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getEmployeesByNameSearchNoResult() {
        when(employeeService.getEmployeesByName(any())).thenReturn(new ArrayList<>());
        ResponseEntity<List<Employee>> response = iEmployeeController.getEmployeesByNameSearch("Diana");
        Assertions.assertEquals(0, response.getBody().size());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getEmployeesByNameSearchFailed() {
        when(employeeService.getEmployeesByName(any())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        ResponseEntity<List<Employee>> response = iEmployeeController.getEmployeesByNameSearch("Diana");
        Assertions.assertEquals(0, response.getBody().size());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getEmployeeById() {
        when(employeeService.getEmployeeById(any())).thenReturn(DummyEmployeeFixtures.getEmployeeMap().get(7));
        ResponseEntity<Employee> response = iEmployeeController.getEmployeeById("7");
        assertEquals(DummyEmployeeFixtures.getEmployeeMap().get(7), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getEmployeeByIdNoResult() {
        when(employeeService.getEmployeeById(any())).thenReturn(null);
        ResponseEntity<Employee> response = iEmployeeController.getEmployeeById("7");
        assertEquals(null, response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getEmployeeByIdFailed() {
        when(employeeService.getEmployeeById(any())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        ResponseEntity<Employee> response = iEmployeeController.getEmployeeById("7");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getHighestSalaryOfEmployees() {
        when(employeeService.getHighestSalary()).thenReturn(130000);
        ResponseEntity<Integer> response = iEmployeeController.getHighestSalaryOfEmployees();
        assertEquals(130000, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getHighestSalaryOfEmployeesNoResult() {
        when(employeeService.getHighestSalary()).thenReturn(0);
        ResponseEntity<Integer> response = iEmployeeController.getHighestSalaryOfEmployees();
        assertEquals(0, response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getHighestSalaryOfEmployeesFailed() {
        when(employeeService.getHighestSalary()).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        ResponseEntity<Integer> response = iEmployeeController.getHighestSalaryOfEmployees();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getTopTenHighestEarningEmployeeNames() {
        List<String> expectedHighestEarnersNames = List.of(
                "Matthew Moore",
                "Louisa Lemon",
                "Kirk Kellie",
                "Jenna Johnson",
                "Ian Ingram",
                "Hannah Hughes",
                "Gregg Greggory",
                "Frankie Fairlop",
                "Ethan Emmerson",
                "Diana Dott"
        );
        when(employeeService.getTenHighestEarningEmployeesNames()).thenReturn(expectedHighestEarnersNames);
        ResponseEntity<List<String>> response = iEmployeeController.getTopTenHighestEarningEmployeeNames();
        Assertions.assertEquals(10, response.getBody().size());
        assertThat(response.getBody(), is(expectedHighestEarnersNames));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getTopTenHighestEarningEmployeeNamesNoResult() {
        when(employeeService.getTenHighestEarningEmployeesNames()).thenReturn(new ArrayList<>());
        ResponseEntity<List<String>> response = iEmployeeController.getTopTenHighestEarningEmployeeNames();
        Assertions.assertEquals(0, response.getBody().size());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getTopTenHighestEarningEmployeeNamesFailed() {
        when(employeeService.getTenHighestEarningEmployeesNames()).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        ResponseEntity<List<String>> response = iEmployeeController.getTopTenHighestEarningEmployeeNames();
        Assertions.assertEquals(0, response.getBody().size());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void createEmployeeSuccess() {
        when(employeeService.createEmployee(any())).thenReturn(true);
        Map<String, Object> dummyEmployeeInput = Map.of(
                "name", "Simon Parks",
                "salary", "100000",
                "age", "42"
        );
        ResponseEntity<String> response = iEmployeeController.createEmployee(dummyEmployeeInput);
        assertEquals("Success", response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createEmployeeFailed() {
        when(employeeService.createEmployee(any())).thenReturn(false);
        Map<String, Object> dummyEmployeeInput = Map.of(
                "name", "Simon Parks",
                "salary", "100000",
                "age", "42"
        );
        ResponseEntity<String> response = iEmployeeController.createEmployee(dummyEmployeeInput);
        assertEquals("Failed", response.getBody());
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    void createEmployeeFailedDueToError() {
        when(employeeService.createEmployee(any())).thenThrow(new HttpClientErrorException(HttpStatus.CONFLICT));
        Map<String, Object> dummyEmployeeInput = Map.of(
                "name", "Simon Parks",
                "salary", "100000",
                "age", "42"
        );
        ResponseEntity<String> response = iEmployeeController.createEmployee(dummyEmployeeInput);
        assertEquals("Failed", response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void deleteEmployeeById() {
        when(employeeService.deleteEmployee(any())).thenReturn("Louisa Lemon");
        ResponseEntity<String> response = iEmployeeController.deleteEmployeeById("12");
        assertEquals("Louisa Lemon", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteEmployeeByIdFailed() {
        when(employeeService.deleteEmployee(any())).thenThrow(new HttpClientErrorException(HttpStatus.CONFLICT));
        ResponseEntity<String> response = iEmployeeController.deleteEmployeeById("12");
        assertEquals("", response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}