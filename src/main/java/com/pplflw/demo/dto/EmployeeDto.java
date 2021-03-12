package com.pplflw.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EmployeeDto {

    private Long id;
    private String username;
    @Email
    @NotNull
    @NotEmpty
    private String email;
    private String firstName;
    private String lastName;
}
