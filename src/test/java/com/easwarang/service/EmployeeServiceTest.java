package com.easwarang.service;

import com.easwarang.dao.DepartmentRepository;
import com.easwarang.dao.EmployeeRepository;
import com.easwarang.dao.RoleRepository;
import com.easwarang.dto.request.EmployeeRequestDTO;
import com.easwarang.entity.DepartmentEntity;
import com.easwarang.entity.EmployeeEntity;
import com.easwarang.entity.RoleEntity;
import com.easwarang.service.impl.EmployeeServiceImpl;
import com.easwarang.utils.MockDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest extends MockDataBuilder {

    @InjectMocks
    @Spy
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private RoleRepository roleRepository;


    @BeforeEach
    void initService() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetEmployeeById() throws ParseException {
        EmployeeEntity employeeEntity = getEmployeeEntity();
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employeeEntity));
        Optional<EmployeeEntity> employeeResult = employeeService.getEmployeeById(any());
        assertTrue(employeeResult.isPresent(), "Employee result should be present");
        EmployeeEntity employee = employeeResult.get();
        assertEquals(1L, employee.getEmployeeId());
        assertEquals("Developer", employee.getEmployeeRole().getRoleName());
    }

    @Test
    public void testGetAllEmployees() throws ParseException {
        List<EmployeeEntity> employeeEntityList = new ArrayList<>();
        employeeEntityList.add(getEmployeeEntity());
        employeeEntityList.add(getEmployeeEntity());
        when(employeeRepository.findAll()).thenReturn(employeeEntityList);
        Optional<List<EmployeeEntity>> employeeResult = employeeService.getAllEmployees();
        assertTrue(employeeResult.isPresent(), "Employee result should be present");
    }

    @Test
    public void testSaveEmployees() throws ParseException {
        Optional<DepartmentEntity> departmentEntity = Optional.ofNullable(getDepartmentEntity());
        Optional<RoleEntity> roleEntity = Optional.ofNullable(getRoleEntity());
        List<EmployeeRequestDTO> employeeRequestDTOList = new ArrayList<>();
        employeeRequestDTOList.add(getEmployeeRequestDTO());
        when(departmentRepository.findById(any())).thenReturn(departmentEntity);
        when(roleRepository.findById(any())).thenReturn(roleEntity);
        employeeService.saveEmployee(employeeRequestDTOList);

        verify(employeeRepository).saveAllAndFlush(any());
    }

    @Test
    public void testUpdateEmployees() throws ParseException {
        Optional<DepartmentEntity> departmentEntity = Optional.ofNullable(getDepartmentEntity());
        Optional<RoleEntity> roleEntity = Optional.ofNullable(getRoleEntity());
        EmployeeRequestDTO employeeRequestDTO = getEmployeeRequestDTO();
        employeeRequestDTO.setEmployeeName("Test2");
        Optional<EmployeeEntity> employeeEntityOptional = Optional.ofNullable(getEmployeeEntity());
        when(employeeRepository.findById(any())).thenReturn(employeeEntityOptional);
        when(departmentRepository.findById(any())).thenReturn(departmentEntity);
        when(roleRepository.findById(any())).thenReturn(roleEntity);
        employeeService.updateEmployee(1L, employeeRequestDTO);
        verify(employeeRepository).save(any());
    }

    @Test
    public void testGetAllEmployeesByHierarchy() throws ParseException {
        Optional<EmployeeEntity> employeeEntityOptional = Optional.ofNullable(getEmployeeEntity());
        when(employeeRepository.findById(1L)).thenReturn(employeeEntityOptional);
        Optional<List<EmployeeEntity>> employeeResult = employeeService.getAllEmployeesByHierarchy(1L);
        assertTrue(employeeResult.isPresent(), "Employee result should be present");
    }

    @Test
    public void testGetAllEmployeesByHierarchyNotFound() {
        Optional<EmployeeEntity> employeeEntityOptional = Optional.empty();
        when(employeeRepository.findById(1L)).thenReturn(employeeEntityOptional);
        Optional<List<EmployeeEntity>> employeeResult = employeeService.getAllEmployeesByHierarchy(1L);
        assertTrue(employeeResult.isPresent(), "Employee result should be present");
    }


}
