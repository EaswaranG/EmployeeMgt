package com.easwarang.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeRequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotNull (message = "employeeId can not be null")
    private Long employeeId;
	@NotNull (message = "employeeName can not be null")
    private String employeeName;
	@NotNull (message = "employeeDob can not be null")
    private String employeeDob;
	@NotNull (message = "employeeEmail can not be null")
    private String employeeEmail;
	@NotNull (message = "employeeSalary can not be null")
    private Double employeeSalary;
	@NotNull (message = "employeeDepartmentId can not be null")
    private Long employeeDepartmentId;
	@NotNull (message = "employeeRole ID can not be null")
    private Long employeeRole;
	
	 private Long reportingManagerId;
}
