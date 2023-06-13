package com.easwarang.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagerResponseDTO implements Serializable {

    private Long managerId;

    private String managerName;

    private String managerDepartment;

    private Long managerCostAllocation;
}
