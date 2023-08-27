package com.gizasystems.controller;

import com.gizasystems.dto.EmployeeDtoRequest;
import com.gizasystems.dto.EmployeeDtoResponse;
import com.gizasystems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class EmployeeController {


    private final EmployeeService employeeService;

    // Create Employee
    @PostMapping("/employee")
    public ResponseEntity<EmployeeDtoResponse> saveEmployee(@RequestBody EmployeeDtoRequest employee) {
        return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.OK);
    }

    // Update Employee
    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeDtoResponse> updateEmployee(@RequestBody EmployeeDtoRequest employee, @PathVariable int id) {
        return new ResponseEntity<>(employeeService.updateEmployee(employee, id), HttpStatus.OK);
    }

    // Read Employee
    @GetMapping("/employee/{name}")
    public ResponseEntity<EmployeeDtoResponse> getEmployeeByName(@PathVariable String name) {
        return new ResponseEntity<>(employeeService.getEmployeeByName(name.toLowerCase()), HttpStatus.OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDtoResponse>> getEmployees() {
        return new ResponseEntity<>(employeeService.getEmployees(), HttpStatus.OK);
    }

    @GetMapping("/employee/id/{id}")
    public ResponseEntity<EmployeeDtoResponse> getEmployeeById(@PathVariable int id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    // Delete Employe
    @DeleteMapping("/employee/{name}")
    public ResponseEntity<String> deleteEmployeeByName(@PathVariable String name) {
        return new ResponseEntity<>(employeeService.deleteEmployeeByName(name), HttpStatus.OK);
    }
}
