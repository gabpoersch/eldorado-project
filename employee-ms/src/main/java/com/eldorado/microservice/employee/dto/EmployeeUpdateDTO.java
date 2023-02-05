package com.eldorado.microservice.employee.dto;

import com.eldorado.microservice.employee.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeUpdateDTO {
    String name;
    String document;
    GenderEnum gender;
    String birthdate;
}

