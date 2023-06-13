package com.easwarang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.easwarang.dto.request.EmployeeRequestDTO;
import com.easwarang.service.EmployeeService;
import com.easwarang.utils.MockDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest extends MockDataBuilder {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveEmployeeDetails_ValidData_ReturnsOK() throws Exception {
        EmployeeRequestDTO employeeRequestDTO = getEmployeeRequestDTO();
        List<EmployeeRequestDTO> employeeRequestDTOList = Collections.singletonList(employeeRequestDTO);
        mockMvc.perform(post("/ems/v1/employee/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString(employeeRequestDTOList))).andExpect(status().isOk());
        verify(employeeService).saveEmployee(any());
    }

}