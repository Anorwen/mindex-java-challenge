package com.mindex.challenge.data;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Compensation {

    @Id
    private String employeeId;
    private int salary;
    private Date startDate;

    public Compensation() {

    }

    public Compensation(String employeeId, int salary, Date startDate) {
        this.employeeId = employeeId;
        this.salary = salary;
        this.startDate = startDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public int getSalary() {
        return salary;
    }

    public Date getStartDate() {
        return startDate;
    }

    public boolean isValid() {
        if(employeeId != null && startDate != null) {
            return true;
        }
        return false;
    }
}
