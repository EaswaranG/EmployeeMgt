package com.easwarang.dto.request;


import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentRequestDTO implements Serializable {

	@NotNull (message = "Department ID can not be null")
    private Long departmentId;

    @NotNull (message = "Department Name can not be null")
    private String departmentName;
}
