package com.easwarang.service;

import com.easwarang.dto.request.EmployeeRequestDTO;
import com.easwarang.entity.EmployeeEntity;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<EmployeeEntity> getEmployeeById(Long employeeId);
    Optional<List<EmployeeEntity>> getAllEmployees();
    void saveEmployee(List<EmployeeRequestDTO> employeeRequestDTOList) throws ParseException;
    void updateEmployee(Long employeeId, EmployeeRequestDTO employeeRequestDTO) throws ParseException;
    void deleteEmployee(Long employeeId);
	Double getCostOfDept(Long deptId);
	Double getCostOfManger(Long managerID);
	Optional<List<EmployeeEntity>> getAllEmployeesByHierarchy(Long employeeId);
}
