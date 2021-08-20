package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationCreateUrl;
    private String compensationReadUrl;

    @Autowired
    private CompensationService compensationService;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationCreateUrl = "http://localhost:" + port + "/compensation";
        compensationReadUrl = "http://localhost:" + port + "employee/{id}/compensation";
    }

    @Test
    public void testCreateRead() {
        String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        Compensation wantedCompensation = new Compensation(employeeId, 10000, Date.from(Instant.now()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Compensation createdCompensation =
                restTemplate.exchange(compensationCreateUrl,
                        HttpMethod.POST,
                        new HttpEntity<Compensation>(wantedCompensation, headers),
                        Compensation.class).getBody();

        if(createdCompensation == null) {
            createdCompensation = new Compensation();
        }

        assertCompensationEquivalence(wantedCompensation, createdCompensation);

        Compensation readCompensation = restTemplate.getForEntity(compensationReadUrl, Compensation.class, employeeId).getBody();

        if(readCompensation == null) {
            readCompensation = new Compensation();
        }

        assertCompensationEquivalence(wantedCompensation, readCompensation);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getStartDate(), actual.getStartDate());
    }
}
