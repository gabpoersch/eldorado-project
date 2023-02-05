package com.eldorado.microservice.employee.config.auth;


import com.eldorado.microservice.employee.enums.Role;
import com.eldorado.microservice.employee.model.Employee;
import com.eldorado.microservice.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthResponse register(RegisterRequest request) {
        Employee employee = Employee.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .document(request.getDocument())
                .gender(request.getGender())
                .birthdate(request.getBirthdate())
                .role(Role.EMPLOYEE)
                .build();
        employeeRepository.save(employee);

        var jwt = jwtService.generateToken(employee);

        return AuthResponse.builder()
                .token(jwt)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        Employee employee = employeeRepository.findByUsername(request.getUsername()).orElseThrow();

        var jwt = jwtService.generateToken(employee);

        return AuthResponse.builder()
                .token(jwt)
                .build();
    }
}
