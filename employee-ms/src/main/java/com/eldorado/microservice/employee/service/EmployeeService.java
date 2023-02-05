package com.eldorado.microservice.employee.service;


import com.eldorado.microservice.employee.dto.EmployeeDTO;
import com.eldorado.microservice.employee.dto.EmployeeRequestDTO;
import com.eldorado.microservice.employee.dto.EmployeeResponseDTO;
import com.eldorado.microservice.employee.dto.EmployeeUpdateDTO;
import com.eldorado.microservice.employee.exception.EmployeeException;

public interface EmployeeService {

    EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employeeRequestDTO);

    EmployeeDTO getEmployee(String id) throws EmployeeException;

    EmployeeDTO updateEmployee(String id, EmployeeUpdateDTO employeeUpdateDTO) throws EmployeeException;

    void deleteEmployee(String id) throws EmployeeException;

}
