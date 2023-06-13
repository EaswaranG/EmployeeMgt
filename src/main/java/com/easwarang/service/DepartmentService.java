package com.easwarang.service;

import com.easwarang.dto.request.DepartmentRequestDTO;
import com.easwarang.dto.response.DepartmentResponseDTO;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Optional<List<DepartmentResponseDTO>> getAllDepartments();
    Optional<DepartmentResponseDTO> getDepartmentByName(String departmentName);
    void saveDepartment(DepartmentRequestDTO departmentRequestDTO);
    void updateDepartment(Long departmentId, DepartmentRequestDTO departmentRequestDTO);
    void deleteDepartment(Long departmentId);
}
