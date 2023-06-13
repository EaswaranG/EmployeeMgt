package com.easwarang.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentResponseDTO implements Serializable {

    private Long departmentId;

    private String departmentName;

    private Long departmentCostAllocation;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<EmployeeResponseDTO> departmentEmployees;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ManagerResponseDTO departmentManager;

}
