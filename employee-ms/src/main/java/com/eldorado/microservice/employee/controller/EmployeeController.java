package com.eldorado.microservice.employee.controller;

import com.eldorado.microservice.employee.dto.EmployeeDTO;
import com.eldorado.microservice.employee.dto.EmployeeRequestDTO;
import com.eldorado.microservice.employee.dto.EmployeeResponseDTO;
import com.eldorado.microservice.employee.dto.EmployeeUpdateDTO;
import com.eldorado.microservice.employee.exception.EmployeeException;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Employee Management System")
@RequestMapping("/employee")
public interface EmployeeController {

    @ApiOperation(value = "Create a new employee", response = EmployeeDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created employee"),
            @ApiResponse(code = 409, message = "Username already exists")
    })
    @PostMapping("/save")
    ResponseEntity<EmployeeResponseDTO> saveEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO);

    @ApiOperation(value = "View a specific employee", response = EmployeeDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved employee"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found")
    })
    @GetMapping("/{username}")
    ResponseEntity<EmployeeDTO> getEmployee(@ApiParam(value = "Username of the employee", required = true) @PathVariable("username") String username) throws EmployeeException;

    @ApiOperation(value = "Update an existing employee", response = EmployeeDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated employee"),
            @ApiResponse(code = 404, message = "The employee you were trying to update is not found")
    })
    @PutMapping("/{username}/update")
    ResponseEntity<EmployeeDTO> updateEmployee(@ApiParam(value = "Username of the employee", required = true) @PathVariable("username") String username, @RequestBody EmployeeUpdateDTO employeeUpdateDTO) throws EmployeeException;

    @ApiOperation(value = "Delete an existing employee", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted employee"),
            @ApiResponse(code = 404, message = "The employee you were trying to delete is not found")
    })
    @DeleteMapping("/{username}/delete")
    ResponseEntity<Void> deleteEmployee(@ApiParam(value = "Username of the employee", required = true) @PathVariable("username") String username) throws EmployeeException;
}
