package com.mindex.challenge.data;

public class ReportingStructure {

    private Employee employee;
    private int numberOfReports;

    public ReportingStructure() {

    }

    public ReportingStructure(Employee employee) {
        this.employee = employee;
        this.numberOfReports = findAllReports(employee) - 1; // Don't Include Yourself
    }

    private int findAllReports(Employee employee) {
        int reportsToProvide = 1;

        if(employee.getDirectReports() == null) {
            return reportsToProvide;
        }

        for(Employee underling : employee.getDirectReports()) {
            reportsToProvide += findAllReports(underling);
        }
        return reportsToProvide;
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }
}
