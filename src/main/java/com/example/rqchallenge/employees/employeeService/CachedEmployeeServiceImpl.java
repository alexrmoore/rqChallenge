package com.example.rqchallenge.employees.employeeService;

import com.example.rqchallenge.employees.EmployeeProvider;
import com.example.rqchallenge.employees.EmployeeService;
import com.example.rqchallenge.employees.EmployeeUtilityClass;
import com.example.rqchallenge.employees.employeeProvider.dto.PostedEmployee;
import com.example.rqchallenge.employees.iEmployeeController.dto.Employee;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A pretty rudimentary implementation of a cache that refreshes based on the interval specified by
 * the rq_challenge.employee_service.caching_refresh_interval property.
 * The goal was to save the service from continually having to hit the https://dummy.restapiexample.com endpoint
 * to fetch much of the same data.
 *
 * With more time, I'd like to make this more sophisticated.
 **/

@Component
@Primary
@ConditionalOnProperty(prefix = "rq_challenge", name = "employee_service.caching_enabled")
public class CachedEmployeeServiceImpl implements EmployeeService {

    private static Logger LOGGER = LoggerFactory.getLogger(CachedEmployeeServiceImpl.class);

    private Map<Integer, Employee> employeeCache = new HashMap<>();

    /**
     * An example use of micrometer for tracking custom metrics, in this case a counter to track cache refreshes.
     * With more time I'd add additional metrics, e.g. for monitoring cache rates, request latency etc.
     * View metrics at http://localhost:2800/actuator/prometheus
     **/
    private final Counter cacheRefreshCount;



    private final EmployeeProvider employeeProvider;

    private final ConversionService conversionService;

    public CachedEmployeeServiceImpl(EmployeeProvider employeeProvider, ConversionService conversionService, MeterRegistry meterRegistry) {
        this.employeeProvider = employeeProvider;
        this.conversionService = conversionService;
        cacheRefreshCount = Counter.builder("cacheRefreshCount")
                .register(meterRegistry);
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employeeCache.values());
    }

    public List<Employee> getEmployeesByName(String searchString) {
        return employeeCache.values().stream()
                .filter(employee -> employee.name().contains(searchString))
                .collect(Collectors.toList());
    }

    public Employee getEmployeeById(String id) {
        return employeeCache.get(Integer.valueOf(id));
    }

    public Integer getHighestSalary() {
        return employeeCache.values().stream()
                .map(Employee::salary)
                .reduce(0, (salary1, salary2) -> EmployeeUtilityClass.getHighestSalary(salary1, salary2));
    }

    public List<String> getTenHighestEarningEmployeesNames() {
        return employeeCache.values().stream()
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
        boolean success = EmployeeUtilityClass.postedAndFetchedEmployeeAreEquivalent(postedEmployee, fetchedEmployee);
        if (success) {
            employeeCache.put(fetchedEmployee.id(), fetchedEmployee);
        }
        return success;
    }

    public String deleteEmployee(String id) {
        String employeeName = getEmployeeById(id).name();
        employeeProvider.deleteEmployee(id);
        employeeCache.remove(Integer.valueOf(id));
        return employeeName;
    }

    @Scheduled(initialDelay = 0, fixedDelayString = "${rq_challenge.employee_service.caching_refresh_interval}")
    public void refreshCache() {
        LOGGER.info("Starting cache refresh.");
        employeeCache = employeeProvider.getEmployees().stream()
                .map(e -> conversionService.convert(e, Employee.class))
                .collect(Collectors.toMap(Employee::id, Function.identity()));
        LOGGER.info("Cache refreshed with {} entries.", employeeCache.size());
        cacheRefreshCount.increment();
    }
}
