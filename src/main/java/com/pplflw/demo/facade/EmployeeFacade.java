package com.pplflw.demo.facade;

import com.pplflw.demo.entity.Employee;
import com.pplflw.demo.mappers.EmployeeMapper;
import com.pplflw.demo.dto.EmployeeDto;
import com.pplflw.demo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeFacade {

    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;


    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        var employee = employeeMapper.mapFromDto(employeeDto);

        var newEmployee = employeeService.createNewEmployee(employee);

        return employeeMapper.mapToDto(newEmployee);
    }
}
