package com.example.rqchallenge.employees.employeeProvider;

import com.example.rqchallenge.employees.*;
import com.example.rqchallenge.employees.employeeProvider.dto.FetchedEmployee;
import com.example.rqchallenge.employees.employeeProvider.dto.PostedEmployee;
import com.example.rqchallenge.employees.employeeProvider.dto.ProviderResponseEmployeeList;
import com.example.rqchallenge.employees.employeeProvider.dto.ProviderResponseSingleEmployee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * The provider that sends the HTTP requests to the https://dummy.restapiexample.com/api/v1 endpoint.
 **/

public class RestEmployeeProvider implements EmployeeProvider {

    private static Logger LOGGER = LoggerFactory.getLogger(RestEmployeeProvider.class);

    private final String baseUrl;
    private final RestTemplate restTemplate;

    public RestEmployeeProvider(String baseUrl, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<FetchedEmployee> getEmployees() {
        String url = String.join("", baseUrl, "/employees");
        LOGGER.info("Sending rest request to {}.", url);
        ResponseEntity<ProviderResponseEmployeeList> response = restTemplate.getForEntity(url, ProviderResponseEmployeeList.class);
        return Arrays.asList(response.getBody().data());
    }

    @Override
    public FetchedEmployee getEmployee(String id) {
        String url = String.join("",baseUrl, "/employee/", id);
        LOGGER.info("Sending rest request to {}.", url);
        ResponseEntity<ProviderResponseSingleEmployee> response = restTemplate.getForEntity(url, ProviderResponseSingleEmployee.class);
        return response.getBody().data();
    }

    @Override
    public FetchedEmployee createEmployee(PostedEmployee postedEmployee) {
        String url = String.join("", baseUrl, "/create" );
        LOGGER.info("Sending rest request to {}.", url);
        ResponseEntity<ProviderResponseSingleEmployee> response = restTemplate.postForEntity(url, postedEmployee, ProviderResponseSingleEmployee.class);
        return response.getBody().data();
    }

    @Override
    public void deleteEmployee(String id) {
        String url = String.join("", baseUrl, "/delete/", id );
        LOGGER.info("Sending rest request to {}.", url);
        restTemplate.delete(url);
    }

}
