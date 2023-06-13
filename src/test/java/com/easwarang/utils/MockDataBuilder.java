package com.easwarang.utils;

import com.easwarang.dto.request.EmployeeRequestDTO;
import com.easwarang.entity.DepartmentEntity;
import com.easwarang.entity.EmployeeEntity;
import com.easwarang.entity.RoleEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MockDataBuilder {

    protected DepartmentEntity getDepartmentEntity() {
        return DepartmentEntity.builder()
                .departmentId(1L)
                .departmentName("HR")
                .build();
    }

    protected RoleEntity getRoleEntity() {
        return RoleEntity.builder().roleId(1L)
                .roleName("Developer")
                .roleDescription("Developer Role")
                .build();
    }

    protected EmployeeRequestDTO getEmployeeRequestDTO() {

        return EmployeeRequestDTO.builder()
                .employeeId(1L)
                .employeeDob("1990-05-27")
                .employeeEmail("test@mastercard.com")
                .employeeName("testEmployee")
                .employeeRole(1L)
                .employeeSalary(400000.0)
                .employeeDepartmentId(1L)
                .build();
    }

    protected EmployeeEntity getEmployeeEntity() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        RoleEntity roleEntity = getRoleEntity();

        DepartmentEntity departmentEntity = getDepartmentEntity();

        return EmployeeEntity.builder()
                .employeeId(1L)
                .employeeDob(dateFormat.parse("1990-05-27"))
                .employeeEmail("test@mastercard.com")
                .employeeName("testEmployee")
                .employeeRole(roleEntity)
                .employeeSalary(400000.0)
                .employeeDepartment(departmentEntity)
                .reportingManagerId(2L)
                .build();
    }
}
