package com.easwarang.service.impl;

import com.easwarang.dao.DepartmentRepository;
import com.easwarang.dao.EmployeeRepository;
import com.easwarang.dao.RoleRepository;
import com.easwarang.dto.request.EmployeeRequestDTO;
import com.easwarang.entity.DepartmentEntity;
import com.easwarang.entity.EmployeeEntity;
import com.easwarang.entity.RoleEntity;
import com.easwarang.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<EmployeeEntity> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public Optional<List<EmployeeEntity>> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return Optional.of(employeeEntities);
    }

    @Override
    public void saveEmployee(List<EmployeeRequestDTO> employeeRequestDTOList) throws ParseException {
        ArrayList<EmployeeEntity> listOfRequests = new ArrayList<>();
        for (EmployeeRequestDTO employeeRequestDTO : employeeRequestDTOList) {
            EmployeeEntity employeeDAO = new EmployeeEntity();
            BeanUtils.copyProperties(employeeRequestDTO, employeeDAO);
            employeeDAO.setEmployeeId(employeeRequestDTO.getEmployeeId());
            employeeDAO.setEmployeeSalary(employeeRequestDTO.getEmployeeSalary());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            employeeDAO.setEmployeeDob(dateFormat.parse(employeeRequestDTO.getEmployeeDob()));

            Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(employeeRequestDTO.getEmployeeDepartmentId());
            Optional<RoleEntity> roleOptional = roleRepository.findById(employeeRequestDTO.getEmployeeRole());

            departmentEntity.ifPresent(employeeDAO::setEmployeeDepartment);

            roleOptional.ifPresent(employeeDAO::setEmployeeRole);

            listOfRequests.add(employeeDAO);
        }
        employeeRepository.saveAllAndFlush(listOfRequests);
    }

    @Override
    public void updateEmployee(Long employeeId, EmployeeRequestDTO employeeRequestDTO) throws ParseException {
        Optional<EmployeeEntity> employeeEntityOptional = employeeRepository.findById(employeeId);
        if (employeeEntityOptional.isPresent()) {
            EmployeeEntity employeeDAO = employeeEntityOptional.get();
            BeanUtils.copyProperties(employeeRequestDTO, employeeDAO);
            employeeDAO.setEmployeeId(employeeRequestDTO.getEmployeeId());
            employeeDAO.setEmployeeSalary(employeeRequestDTO.getEmployeeSalary());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            employeeDAO.setEmployeeDob(dateFormat.parse(employeeRequestDTO.getEmployeeDob()));

            Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(employeeRequestDTO.getEmployeeDepartmentId());
            Optional<RoleEntity> roleOptional = roleRepository.findById(employeeRequestDTO.getEmployeeRole());

            departmentEntity.ifPresent(employeeDAO::setEmployeeDepartment);
            roleOptional.ifPresent(employeeDAO::setEmployeeRole);
            employeeRepository.save(employeeDAO);

            BeanUtils.copyProperties(employeeDAO, employeeRequestDTO);
        }
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Optional<EmployeeEntity> employeeEntityOptional = employeeRepository.findById(employeeId);
        employeeEntityOptional.ifPresent(employeeEntity -> employeeRepository.delete(employeeEntity));
    }

    @Override
    public Double getCostOfDept(Long deptId) {
        return employeeRepository.costByDept(deptId);
    }

    @Override
    public Double getCostOfManger(Long managerID) {
        return employeeRepository.costByManager(managerID);
    }

    @Override
    public Optional<List<EmployeeEntity>> getAllEmployeesByHierarchy(Long employeeId) {
        boolean isReportingExist = false;
        ArrayList<EmployeeEntity> listOfRequests = new ArrayList<>();
        Optional<EmployeeEntity> selectedEmpl = employeeRepository.findById(employeeId);
        selectedEmpl.ifPresent(listOfRequests::add);

        isReportingExist = selectedEmpl.isPresent() && selectedEmpl.get().getReportingManagerId() != null;

        while (isReportingExist) {
            selectedEmpl = employeeRepository.findById(selectedEmpl.get().getReportingManagerId());
            selectedEmpl.ifPresent(listOfRequests::add);
            if (!selectedEmpl.isPresent() || selectedEmpl.get().getReportingManagerId() == null)
                isReportingExist = false;
        }
        Collections.reverse(listOfRequests);
        return Optional.of(listOfRequests);
    }
}
