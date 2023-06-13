package com.easwarang.dto.request;


import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagerRequestDTO implements Serializable {

    @NotNull (message = "Manager ID can not be null")
    private Long managerId;

    @NotNull (message = "Manager Name can not be null")
    private String managerName;

    @NotNull (message = "Department ID can not be null")
    private Long managerDepartmentId;
}
