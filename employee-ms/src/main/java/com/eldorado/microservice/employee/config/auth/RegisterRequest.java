package com.eldorado.microservice.employee.config.auth;

import com.eldorado.microservice.employee.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String password;
    private String name;
    private String document;
    private GenderEnum gender;
    private String birthdate;

}
