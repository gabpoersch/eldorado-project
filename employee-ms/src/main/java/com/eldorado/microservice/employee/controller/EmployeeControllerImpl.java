package com.eldorado.microservice.employee.controller;


import com.eldorado.microservice.employee.dto.EmployeeDTO;
import com.eldorado.microservice.employee.dto.EmployeeRequestDTO;
import com.eldorado.microservice.employee.dto.EmployeeResponseDTO;
import com.eldorado.microservice.employee.dto.EmployeeUpdateDTO;
import com.eldorado.microservice.employee.exception.EmployeeException;
import com.eldorado.microservice.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeControllerImpl implements EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ResponseEntity<EmployeeResponseDTO> saveEmployee(EmployeeRequestDTO employeeRequestDTO) {
        return new ResponseEntity<>(employeeService.saveEmployee(employeeRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<EmployeeDTO> getEmployee(String username) throws EmployeeException {
        return new ResponseEntity<>(employeeService.getEmployee(username), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EmployeeDTO> updateEmployee(String username, EmployeeUpdateDTO employeeUpdateDTO) throws EmployeeException {
        return new ResponseEntity<>(employeeService.updateEmployee(username, employeeUpdateDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteEmployee(String username) throws EmployeeException {
        employeeService.deleteEmployee(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
