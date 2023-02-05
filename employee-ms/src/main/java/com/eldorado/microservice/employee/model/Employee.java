package com.eldorado.microservice.employee.model;


import com.eldorado.microservice.employee.enums.GenderEnum;
import com.eldorado.microservice.employee.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "password")
@Document(collection = "employee")
public class Employee implements UserDetails {

    @Id
    private String username;

    @Field("password")
    private String password;

    @Field("name")
    private String name;

    @Field("document")
    private String document;

    @Field("gender")
    private GenderEnum gender;

    @Field("birthdate")
    private String birthdate;

    @Field("role")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
