package com.pplflw.demo.mappers;

import com.pplflw.demo.entity.Employee;
import com.pplflw.demo.dto.EmployeeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee mapFromDto(EmployeeDto dto);

    EmployeeDto mapToDto(Employee employee);

}
