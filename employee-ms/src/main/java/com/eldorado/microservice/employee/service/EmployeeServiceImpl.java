package com.eldorado.microservice.employee.service;


import com.eldorado.microservice.employee.dto.EmployeeDTO;
import com.eldorado.microservice.employee.dto.EmployeeSaveDTO;
import com.eldorado.microservice.employee.dto.EmployeeUpdateDTO;
import com.eldorado.microservice.employee.exception.EmployeeException;
import com.eldorado.microservice.employee.mapper.EmployeeMapper;
import com.eldorado.microservice.employee.model.Employee;
import com.eldorado.microservice.employee.repository.EmployeeRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RabbitTemplate rabbitTemplate;

    public static final String SAVE_QUEUE = "save-registry";
    public static final String UPDATE_QUEUE = "update-registry";
    public static final String DELETE_QUEUE = "delete-registry";

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RabbitTemplate rabbitTemplate) {
        this.employeeRepository = employeeRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeSaveDTO employeeSaveDTO) {
        Employee employee = EmployeeMapper.saveDtoToEntity(employeeSaveDTO);
        Employee savedEmployee = employeeRepository.save(employee);

        Message message = new Message(employeeSaveDTO.toString().getBytes());
        rabbitTemplate.send(SAVE_QUEUE, message);

        return EmployeeMapper.entityToDto(savedEmployee);
    }

    @Override
    public EmployeeDTO getEmployee(String username) throws EmployeeException {
        return employeeRepository.findByUsername(username)
                .map(EmployeeMapper::entityToDto)
                .orElseThrow(() -> new EmployeeException(EmployeeException.USER_NOT_FOUND));
    }

    @Override
    public EmployeeDTO updateEmployee(String username, EmployeeUpdateDTO employeeUpdateDTO) throws EmployeeException {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new EmployeeException(EmployeeException.USER_NOT_FOUND));

        employee.setName(employeeUpdateDTO.getName());
        employee.setDocument(employeeUpdateDTO.getDocument());
        employee.setGender(employeeUpdateDTO.getGender());
        employee.setBirthdate(employeeUpdateDTO.getBirthdate());

        employeeRepository.save(employee);

        Message message = new Message(employee.toString().getBytes());
        rabbitTemplate.send(UPDATE_QUEUE, message);

        return EmployeeMapper.entityToDto(employee);
    }

    @Override
    public void deleteEmployee(String username) throws EmployeeException {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new EmployeeException(EmployeeException.USER_NOT_FOUND));

        employeeRepository.delete(employee);

        Message message = new Message(employee.toString().getBytes());
        rabbitTemplate.send(DELETE_QUEUE, message);
    }
}
