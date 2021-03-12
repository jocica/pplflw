package com.pplflw.demo.services;

import com.pplflw.demo.entity.Employee;
import com.pplflw.demo.exceptions.EmployeeNotFoundException;
import com.pplflw.demo.repository.EmployeeRepository;
import com.pplflw.demo.states.EmployeeState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public Employee findEmployee(Long employeeId) {
        var employeeO =  employeeRepository.findById(employeeId);
        return employeeO.orElseThrow(EmployeeNotFoundException::new);
    }

    @Transactional
    public Employee changeStateOfEmployee(Long employeeId, EmployeeState state) {
        var employee = findEmployee(employeeId);
        employee.setState(state);
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee createNewEmployee(Employee employee) {
        employee.setState(EmployeeState.ADDED);
        employee.setDataCreated(Instant.now());
        return employeeRepository.save(employee);
    }
}
