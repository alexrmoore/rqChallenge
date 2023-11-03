package com.example.rqchallenge.employees.employeeService;

import com.example.rqchallenge.employees.DummyEmployeeFixtures;
import com.example.rqchallenge.employees.EmployeeProvider;
import com.example.rqchallenge.employees.converter.ProvidedEmployeeToEmployeeConverter;
import com.example.rqchallenge.employees.employeeProvider.dto.PostedEmployee;
import com.example.rqchallenge.employees.iEmployeeController.dto.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.support.GenericConversionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BasicEmployeeServiceImplTest {

    @Mock
    private EmployeeProvider employeeProvider;

    private GenericConversionService conversionService = new GenericConversionService();

    private BasicEmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        conversionService.addConverter(new ProvidedEmployeeToEmployeeConverter());
        employeeService = new BasicEmployeeServiceImpl(employeeProvider, conversionService);
    }

    @Test
    void getEmployees() {
        when(employeeProvider.getEmployees()).thenReturn(new ArrayList<>(DummyEmployeeFixtures.getFetchedEmployeeMap().values()));
        List<Employee> employees = employeeService.getEmployees();
        verify(employeeProvider, times(1)).getEmployees();
        Assertions.assertEquals(13, employees.size());
        List<Employee> expectedEmployees = new ArrayList<>(DummyEmployeeFixtures.getEmployeeMap().values());
        assertThat(employees, is(expectedEmployees));
    }

    @Test
    void getEmployeesByName() {
        when(employeeProvider.getEmployees()).thenReturn(new ArrayList<>(DummyEmployeeFixtures.getFetchedEmployeeMap().values()));
        List<Employee> employees = employeeService.getEmployeesByName("Frank");
        verify(employeeProvider, times(1)).getEmployees();
        Assertions.assertEquals(1, employees.size());
        Employee expectedEmployee = DummyEmployeeFixtures.getEmployeeMap().get(6);
        Assertions.assertEquals(expectedEmployee, employees.get(0));
    }

    @Test
    void getEmployeeById() {
        when(employeeProvider.getEmployee("4")).thenReturn(DummyEmployeeFixtures.getFetchedEmployeeMap().get(4));
        Employee employee = employeeService.getEmployeeById("4");
        verify(employeeProvider, times(1)).getEmployee(any());
        Employee expectedEmployee = DummyEmployeeFixtures.getEmployeeMap().get(4);
        Assertions.assertEquals(expectedEmployee, employee);
    }

    @Test
    void getHighestSalary() {
        when(employeeProvider.getEmployees()).thenReturn(new ArrayList<>(DummyEmployeeFixtures.getFetchedEmployeeMap().values()));
        Integer highestSalary = employeeService.getHighestSalary();
        verify(employeeProvider, times(1)).getEmployees();
        Assertions.assertEquals(130000, highestSalary);
    }

    @Test
    void getTenHighestEarningEmployeesNames() {
        when(employeeProvider.getEmployees()).thenReturn(new ArrayList<>(DummyEmployeeFixtures.getFetchedEmployeeMap().values()));
        List<String> highestEarners = employeeService.getTenHighestEarningEmployeesNames();
        verify(employeeProvider, times(1)).getEmployees();
        List<String> expectedHighestEarners = List.of(
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
        assertThat(highestEarners, is(expectedHighestEarners));
    }

    @Test
    void createEmployeeSuccess() {
        when(employeeProvider.createEmployee(any(PostedEmployee.class))).thenReturn(DummyEmployeeFixtures.getFetchedEmployeeMap().get(7));
        Map<String, Object> employeeInput = Map.of(
                "name", "Gregg Greggory",
                "salary", "70000",
                "age", "27"
        );
        boolean success = employeeService.createEmployee(employeeInput);
        verify(employeeProvider, times(1)).createEmployee(any());
        Assertions.assertTrue(success);
    }

    @Test
    void createEmployeeFailed() {
        when(employeeProvider.createEmployee(any(PostedEmployee.class))).thenReturn(DummyEmployeeFixtures.getFetchedEmployeeMap().get(7));
        Map<String, Object> employeeInput = Map.of(
                "name", "Not Gregg Greggory",
                "salary", "71000",
                "age", "28"
        );
        boolean success = employeeService.createEmployee(employeeInput);
        verify(employeeProvider, times(1)).createEmployee(any());
        Assertions.assertFalse(success);
    }

    @Test
    void deleteEmployee() {
        when(employeeProvider.getEmployee("8")).thenReturn(DummyEmployeeFixtures.getFetchedEmployeeMap().get(8));
        String deletedName = employeeService.deleteEmployee("8");
        verify(employeeProvider, times(1)).getEmployee(any());
        verify(employeeProvider, times(1)).deleteEmployee(any());
        Assertions.assertEquals("Hannah Hughes", deletedName);
    }
}