package com.eldorado.microservice.employee.service;


import com.eldorado.microservice.employee.config.auth.JwtService;
import com.eldorado.microservice.employee.dto.EmployeeDTO;
import com.eldorado.microservice.employee.dto.EmployeeRequestDTO;
import com.eldorado.microservice.employee.dto.EmployeeResponseDTO;
import com.eldorado.microservice.employee.dto.EmployeeUpdateDTO;
import com.eldorado.microservice.employee.enums.GenderEnum;
import com.eldorado.microservice.employee.enums.Role;
import com.eldorado.microservice.employee.exception.EmployeeException;
import com.eldorado.microservice.employee.mapper.EmployeeMapper;
import com.eldorado.microservice.employee.model.Employee;
import com.eldorado.microservice.employee.repository.EmployeeRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RabbitTemplate rabbitTemplate;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public static final String SAVE_QUEUE = "save-registry";
    public static final String UPDATE_QUEUE = "update-registry";
    public static final String DELETE_QUEUE = "delete-registry";

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RabbitTemplate rabbitTemplate, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.employeeRepository = employeeRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = Employee.builder()
                .username(employeeRequestDTO.getUsername())
                .password(passwordEncoder.encode(employeeRequestDTO.getPassword()))
                .name(employeeRequestDTO.getName())
                .document(employeeRequestDTO.getDocument())
                .gender(employeeRequestDTO.getGender())
                .birthdate(employeeRequestDTO.getBirthdate())
                .role(Role.EMPLOYEE)
                .build();
        employeeRepository.save(employee);

        Message message = new Message(employeeRequestDTO.toString().getBytes());
        rabbitTemplate.send(SAVE_QUEUE, message);

        var jwt = jwtService.generateToken(employee);

        return EmployeeResponseDTO.builder()
                .username(employee.getUsername())
                .name(employee.getName())
                .document(employee.getDocument())
                .gender(employee.getGender())
                .birthdate(employee.getBirthdate())
                .token(jwt)
                .build();
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
