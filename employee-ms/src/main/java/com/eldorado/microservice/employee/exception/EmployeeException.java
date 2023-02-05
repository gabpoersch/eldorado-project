package com.eldorado.microservice.employee.exception;

public class EmployeeException extends Exception {
    public static final String USER_NOT_FOUND = "User not found!";
    public static final String ALREADY_REGISTERED = "Employee with the same username already exists.";

    public EmployeeException(String message) {
        super(message);
    }
}
