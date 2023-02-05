package com.eldorado.microservice.employee.service;

import com.eldorado.microservice.employee.dto.EmployeeDTO;
import com.eldorado.microservice.employee.dto.EmployeeSaveDTO;
import com.eldorado.microservice.employee.dto.EmployeeUpdateDTO;
import com.eldorado.microservice.employee.enums.GenderEnum;
import com.eldorado.microservice.employee.exception.EmployeeException;
import com.eldorado.microservice.employee.mapper.EmployeeMapper;
import com.eldorado.microservice.employee.model.Employee;
import com.eldorado.microservice.employee.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    private Employee employee;
    private EmployeeSaveDTO employeeSaveDTO;
    private EmployeeUpdateDTO employeeUpdateDTO;
    private final String username = "username";

    @Before
    public void setUp() {
        employee = new Employee();
        employee.setUsername(username);
        employee.setName("name");
        employee.setDocument("document");
        employee.setGender(GenderEnum.F);
        employee.setBirthdate("birthdate");

        employeeSaveDTO = new EmployeeSaveDTO();
        employeeSaveDTO.setUsername(username);
        employeeSaveDTO.setName("name");
        employeeSaveDTO.setDocument("document");
        employeeSaveDTO.setGender(GenderEnum.F);
        employeeSaveDTO.setBirthdate("birthdate");

        employeeUpdateDTO = new EmployeeUpdateDTO();
        employeeUpdateDTO.setName("newName");
        employeeUpdateDTO.setDocument("newDocument");
        employeeUpdateDTO.setGender(GenderEnum.F);
        employeeUpdateDTO.setBirthdate("newBirthdate");
    }

    @Test
    public void saveEmployee_shouldSaveEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        EmployeeDTO savedEmployee = employeeServiceImpl.saveEmployee(employeeSaveDTO);

        assertEquals(EmployeeMapper.entityToDto(employee), savedEmployee);
    }

    @Test
    public void getEmployee_shouldReturnEmployee() throws EmployeeException {
        when(employeeRepository.findByUsername(username)).thenReturn(Optional.of(employee));

        EmployeeDTO foundEmployee = employeeServiceImpl.getEmployee(username);

        assertEquals(EmployeeMapper.entityToDto(employee), foundEmployee);
    }

    @Test
    public void getEmployee_shouldThrowEmployeeException() {
        when(employeeRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EmployeeException.class, () -> employeeServiceImpl.getEmployee(username));
    }

    @Test
    public void updateEmployee_shouldUpdateEmployee() throws EmployeeException {
        when(employeeRepository.findByUsername(username)).thenReturn(Optional.of(employee));

        EmployeeDTO updatedEmployee = employeeServiceImpl.updateEmployee(username, employeeUpdateDTO);

        assertEquals(EmployeeMapper.entityToDto(employee), updatedEmployee);
    }

    @Test
    public void updateEmployee_shouldThrowEmployeeException() {
        when(employeeRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EmployeeException.class, () -> employeeServiceImpl.updateEmployee(username, employeeUpdateDTO));
    }

    @Test
    public void deleteEmployee_shouldDeleteEmployee() throws EmployeeException {
        when(employeeRepository.findByUsername(username)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        employeeServiceImpl.deleteEmployee(username);

        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    public void deleteEmployee_shouldThrowEmployeeException() {
        when(employeeRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EmployeeException.class, () -> employeeServiceImpl.deleteEmployee(username));
    }


    @Test
    public void saveEmployee_shouldMapEmployeeSaveDtoToEntity() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO savedEmployee = employeeServiceImpl.saveEmployee(employeeSaveDTO);

        assertEquals(EmployeeMapper.saveDtoToEntity(employeeSaveDTO), employee);
    }

}
