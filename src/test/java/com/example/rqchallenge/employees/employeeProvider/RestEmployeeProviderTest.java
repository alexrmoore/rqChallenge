package com.example.rqchallenge.employees.employeeProvider;

import com.example.rqchallenge.employees.DummyEmployeeFixtures;
import com.example.rqchallenge.employees.employeeProvider.dto.FetchedEmployee;
import com.example.rqchallenge.employees.employeeProvider.dto.PostedEmployee;
import com.example.rqchallenge.employees.employeeProvider.dto.ProviderResponseEmployeeList;
import com.example.rqchallenge.employees.employeeProvider.dto.ProviderResponseSingleEmployee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestEmployeeProviderTest {

    private String dummyUrl = "dummy-url.com";

    @Mock
    private RestTemplate restTemplate;

    private RestEmployeeProvider employeeProvider;

    @BeforeEach
    void setUp() {
        employeeProvider = new RestEmployeeProvider(dummyUrl, restTemplate);
    }
    @Test
    void getEmployees() {
        FetchedEmployee[] expectedFetchedEmployees = DummyEmployeeFixtures.getFetchedEmployeeMap().values().toArray(new FetchedEmployee[0]);
        when(restTemplate.getForEntity("dummy-url.com/employees", ProviderResponseEmployeeList.class))
                .thenReturn(new ResponseEntity(new ProviderResponseEmployeeList("success", expectedFetchedEmployees, "message"), HttpStatus.OK));
        List<FetchedEmployee> fetchedEmployees = employeeProvider.getEmployees();
        verify(restTemplate, times(1)).getForEntity("dummy-url.com/employees", ProviderResponseEmployeeList.class);
        Assertions.assertArrayEquals(expectedFetchedEmployees, fetchedEmployees.toArray());
    }

    @Test
    void getEmployee() {
        FetchedEmployee expectedFetchedEmployee = DummyEmployeeFixtures.getFetchedEmployeeMap().get(5);
        when(restTemplate.getForEntity("dummy-url.com/employee/5", ProviderResponseSingleEmployee.class))
                .thenReturn(new ResponseEntity(new ProviderResponseSingleEmployee("success", expectedFetchedEmployee, "message"), HttpStatus.OK));
        FetchedEmployee fetchedEmployee = employeeProvider.getEmployee("5");
        verify(restTemplate, times(1)).getForEntity("dummy-url.com/employee/5", ProviderResponseSingleEmployee.class);
        Assertions.assertEquals(expectedFetchedEmployee, fetchedEmployee);
    }

    @Test
    void createEmployee() {
        FetchedEmployee expectedFetchedEmployee = DummyEmployeeFixtures.getFetchedEmployeeMap().get(9);
        PostedEmployee postedEmployee = new PostedEmployee("Ian Ingram", "90000", "29");
        when(restTemplate.postForEntity("dummy-url.com/create", postedEmployee, ProviderResponseSingleEmployee.class))
                .thenReturn(new ResponseEntity(new ProviderResponseSingleEmployee("success", expectedFetchedEmployee, null), HttpStatus.OK));
        FetchedEmployee fetchedEmployee = employeeProvider.createEmployee(postedEmployee);
        verify(restTemplate, times(1)).postForEntity("dummy-url.com/create", postedEmployee, ProviderResponseSingleEmployee.class);
        Assertions.assertEquals(expectedFetchedEmployee, fetchedEmployee);
    }

    @Test
    void deleteEmployee() {
        employeeProvider.deleteEmployee("5");
        verify(restTemplate, times(1)).delete("dummy-url.com/delete/5");
    }
}