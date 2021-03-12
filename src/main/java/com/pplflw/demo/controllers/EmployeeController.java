package com.pplflw.demo.controllers;


import com.pplflw.demo.dto.EmployeeDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/employees")
public interface EmployeeController {

    @PostMapping
    EmployeeDto createEmployee(@Valid @RequestBody EmployeeDto employeeDto);
}
