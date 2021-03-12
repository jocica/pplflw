package com.pplflw.demo.controllers.impl;

import com.pplflw.demo.controllers.EmployeeController;
import com.pplflw.demo.dto.EmployeeDto;
import com.pplflw.demo.facade.EmployeeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeFacade employeeFacade;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        return employeeFacade.createEmployee(employeeDto);
    }
}
