package com.pplflw.demo.services;

import com.pplflw.demo.entity.Employee;
import com.pplflw.demo.exceptions.EmployeeNotFoundException;
import com.pplflw.demo.repository.EmployeeRepository;
import com.pplflw.demo.states.EmployeeState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private  EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    private static long ID = 8L;

    @Test
    void findEmployee() {

        Employee employee = new Employee();
        employee.setId(ID);

        when(employeeRepository.findById(eq(ID))).thenReturn(Optional.of(employee));

        var result = employeeService.findEmployee(ID);

        assertEquals(employee.getId(), result.getId());

        verify(employeeRepository, times(1)).findById(eq(ID));

    }


    @Test
    void findEmployee_NotFound() {

        when(employeeRepository.findById(any())).thenReturn(Optional.empty());

        var employeeNotFoundException = assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployee(9L));

        assertNotNull(employeeNotFoundException);

    }

    @Test
    void changeStateOfEmployee() {

        Employee employee = new Employee();
        employee.setId(ID);

        when(employeeRepository.findById(eq(ID))).thenReturn(Optional.of(employee));

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);


        var employeeResult = employeeService.changeStateOfEmployee(ID, EmployeeState.ADDED);

        assertEquals(employee.getState(), employeeResult.getState());
    }

    @Test
    void createNewEmployee() {

        Employee employee = new Employee();

        when(employeeRepository.save(any(Employee.class))).thenAnswer(i -> i.getArguments()[0]);

        var newEmployee = employeeService.createNewEmployee(employee);

        assertEquals(EmployeeState.ADDED, newEmployee.getState());

    }
}