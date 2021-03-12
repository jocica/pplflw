package com.pplflw.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pplflw.demo.dto.EmployeeDto;
import com.pplflw.demo.facade.EmployeeFacade;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeFacade employeeFacade;

    private final static  String ENDPOINT = "/employees";

    @Test
    @SneakyThrows
    void testCreateEmployee(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmail("me@gmail.com");
        employeeDto.setUsername("me");
        employeeDto.setFirstName("VFr");
        employeeDto.setId(8L);

        when(employeeFacade.createEmployee(any(EmployeeDto.class))).thenReturn(employeeDto);

        mockMvc.perform(post(ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(employeeDto.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(employeeDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employeeDto.getId()));
    }


    @Test
    @SneakyThrows
    void testCreateEmployee_NoEmail(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("VFr");
        employeeDto.setId(8L);

        when(employeeFacade.createEmployee(any(EmployeeDto.class))).thenReturn(employeeDto);

        mockMvc.perform(post(ENDPOINT)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isBadRequest());
    }

}