package com.pplflw.demo.entity;

import com.pplflw.demo.states.EmployeeState;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Entity
@Table(name = "EMPLOYEE")
public class Employee  extends  BaseEntity{

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_state")
    private EmployeeState state = EmployeeState.ADDED;

    @Column(name = "date_created")
    private Instant dataCreated= Instant.now();

}
