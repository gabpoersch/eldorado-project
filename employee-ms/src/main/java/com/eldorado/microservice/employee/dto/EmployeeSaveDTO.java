package com.eldorado.microservice.employee.dto;

import com.eldorado.microservice.employee.enums.GenderEnum;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = "password")
public class EmployeeSaveDTO {
    String username;
    String password;
    String name;
    String document;
    GenderEnum gender;
    String birthdate;
}

