package com.easwarang.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeResponseDTO implements Serializable {

    private Long employeeId;

    private String employeeName;

    private Date employeeDob;

    private String employeeEmail;

    private Double employeeSalary;

    private String employeeDepartment;

    private String employeeRole;

    private String employeeManager;
}
