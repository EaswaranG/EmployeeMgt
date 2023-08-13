package com.easwarang.controller;


import com.easwarang.dto.request.EmployeeRequestDTO;
import com.easwarang.entity.EmployeeEntity;
import com.easwarang.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/ems/v1/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeEntity>> getAllEmployees() {
        Optional<List<EmployeeEntity>> employeeResponse = employeeService.getAllEmployees();
        return employeeResponse.map(employeeEntity -> new ResponseEntity<>(employeeEntity, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/employeesByHierarchy/{employeeId}")
    public ResponseEntity<List<EmployeeEntity>> getAllEmployeesByHierarchy(@PathVariable(value = "employeeId") Long employeeId) {
        Optional<List<EmployeeEntity>> employeeResponse = employeeService.getAllEmployeesByHierarchy(employeeId);
        return employeeResponse.map(employeeEntity -> new ResponseEntity<>(employeeEntity, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/costbyDept/{deptID}")
    public ResponseEntity<?> getCostOfDept(@PathVariable(value = "deptID") Long deptID) {
        Double employeeResponse = employeeService.getCostOfDept(deptID);
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @GetMapping("/costByManager/{managerID}")
    public ResponseEntity<?> getCostOfManger(@PathVariable(value = "managerID") Long managerID) {
        Double employeeResponse = employeeService.getCostOfManger(managerID);
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeEntity> getEmployeeById(@PathVariable(value = "employeeId") Long employeeId) {
        Optional<EmployeeEntity> employeeResponse = employeeService.getEmployeeById(employeeId);
        return employeeResponse.map(employeeEntity -> new ResponseEntity<>(employeeEntity, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping(path = "/save", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<String> saveEmployeeDetails(@Valid @RequestBody List<EmployeeRequestDTO> employeeRequestDTOList) throws ParseException {
        employeeService.saveEmployee(employeeRequestDTOList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/update/{employeeId}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateEmployeeDetails(@PathVariable(value = "employeeId") Long employeeId,
                                                        @RequestBody EmployeeRequestDTO employeeRequestDTO) throws ParseException {

        employeeService.updateEmployee(employeeId, employeeRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(value = "employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
