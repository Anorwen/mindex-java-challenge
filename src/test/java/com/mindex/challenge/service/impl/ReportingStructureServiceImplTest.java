package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String structureUrl;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        structureUrl = "http://localhost:" + port + "/employee/{id}/reporting-structure/";
    }

    @Test
    public void testRead() {
        String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        Employee expectedEmployee = employeeService.read(employeeId);

        // Test Read Before Change
        ReportingStructure biggestFish = restTemplate.getForEntity(structureUrl, ReportingStructure.class, employeeId).getBody();
        assertRightNumber(4, biggestFish.getNumberOfReports());
        assertRightEmployee(expectedEmployee, biggestFish.getEmployee());

        // Change an Employee
        expectedEmployee.setDirectReports(null);
        employeeService.update(expectedEmployee);

        // Test Read After Change
        biggestFish = restTemplate.getForEntity(structureUrl, ReportingStructure.class, employeeId).getBody();
        assertRightNumber(0, biggestFish.getNumberOfReports());
        assertRightEmployee(expectedEmployee, biggestFish.getEmployee());
    }

    private static void assertRightNumber(int expected, int actual) {
        assertEquals(expected, actual);
    }

    private static void assertRightEmployee(Employee expected, Employee actual) {
        assertEquals(true, expected.equals(actual));
    }
}
