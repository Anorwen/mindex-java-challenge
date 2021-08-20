package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Creating reporting structure for employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        employee = cascadeEmployeeStructure(employee);

        return new ReportingStructure(employee);
    }

    private Employee cascadeEmployeeStructure(Employee employee) {
        if(employee.getDirectReports() == null) {
            return employee;
        }
        
        List<Employee> updatedEmployees = new ArrayList<Employee>();
        Employee updatedUnderling;
        for(Employee underling : employee.getDirectReports()) {
            updatedUnderling = employeeRepository.findByEmployeeId(underling.getEmployeeId());
            updatedEmployees.add(cascadeEmployeeStructure(updatedUnderling));
        }

        employee.setDirectReports(updatedEmployees);
        return employee;
    }
}
