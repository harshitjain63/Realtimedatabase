package com.example.realtimedatabase;

public class Attendance_Model {

    private String employeeName;
    private String employeeattendance;
    private  String Date;

    public Attendance_Model() {
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeattendance() {
        return employeeattendance;
    }

    public void setEmployeeattendance(String employeeattendance) {
        this.employeeattendance = employeeattendance;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
