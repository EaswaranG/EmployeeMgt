package com.easwarang.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_dob", columnDefinition = "date")
    private Date employeeDob;

    @Column(name = "employee_email")
    private String employeeEmail;

    @Column(name = "employee_salary")
    private Double employeeSalary;

    @OneToOne (cascade = CascadeType.PERSIST)
    @JsonManagedReference
    @JoinColumn(name = "department_id")
    private DepartmentEntity employeeDepartment;

    @OneToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    private RoleEntity employeeRole;
    @Column(name = "reporting_id")
    private Long reportingManagerId;

}
