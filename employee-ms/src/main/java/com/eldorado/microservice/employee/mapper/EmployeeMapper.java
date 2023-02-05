package com.eldorado.microservice.employee.mapper;

import com.eldorado.microservice.employee.dto.EmployeeDTO;
import com.eldorado.microservice.employee.dto.EmployeeRequestDTO;
import com.eldorado.microservice.employee.dto.EmployeeUpdateDTO;
import com.eldorado.microservice.employee.model.Employee;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public static EmployeeDTO entityToDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public static Employee dtoToEntity(EmployeeDTO employeeDto) {
        if (employeeDto == null) {
            return null;
        }
        return modelMapper.map(employeeDto, Employee.class);
    }

    public static EmployeeRequestDTO entityToRequestDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        return modelMapper.map(employee, EmployeeRequestDTO.class);
    }

    public static Employee requestDtoToEntity(EmployeeRequestDTO employeeRequestDTO) {
        if (employeeRequestDTO == null) {
            return null;
        }
        return modelMapper.map(employeeRequestDTO, Employee.class);
    }

    public static EmployeeUpdateDTO entityToUpdateDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        return modelMapper.map(employee, EmployeeUpdateDTO.class);
    }

    public static Employee updateDtoToEntity(EmployeeUpdateDTO employeeUpdateDTO) {
        if (employeeUpdateDTO == null) {
            return null;
        }
        return modelMapper.map(employeeUpdateDTO, Employee.class);
    }

}
