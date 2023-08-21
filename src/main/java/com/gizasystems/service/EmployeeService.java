package com.gizasystems.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.gizasystems.dto.EmployeeDtoRequest;
import com.gizasystems.dto.EmployeeDtoResponse;
import com.gizasystems.entity.Employee;
import com.gizasystems.repository.EmployeeRepo;

@Service
@AllArgsConstructor
public class EmployeeService {


    private final EmployeeRepo employeeRepo;


    private final EmployeeDataValidation employeeDataValidation;

    private final ModelMapper modelMapper = new ModelMapper();

    public EmployeeDtoResponse saveEmployee(EmployeeDtoRequest employeeDto) {


        Optional<Employee> employee = Optional.ofNullable(employeeRepo.findByName(employeeDto.getName()));
        validateEmployee(employeeDto);
        if (employee.isPresent()) {
            throw new RuntimeException("Employee Name " + employeeDto.getName() + " Already Exist");

        } else {


            Employee emp = modelMapper.map(employeeDto, Employee.class);

            return modelMapper.map(employeeRepo.save(emp), EmployeeDtoResponse.class);

        }

    }


    public EmployeeDtoResponse getEmployeeByName(String name) {

        Optional<Employee> employee = Optional.ofNullable(employeeRepo.findByName(name));
        if (employee.isPresent()) {
            return modelMapper.map(employeeRepo.findByName(name), EmployeeDtoResponse.class);

        } else {

            throw new RuntimeException("Employee Name " + name + " Not Exist");
        }
    }

    public List<EmployeeDtoResponse> getEmployees() {

        List<EmployeeDtoResponse> employees = new ArrayList<>();
        for (Employee employee : employeeRepo.findAll())
            employees.add(modelMapper.map(employee, EmployeeDtoResponse.class));

        return employees;
    }

    public String deleteEmployeeByName(String name) {

        Optional<Employee> employee = Optional.ofNullable(employeeRepo.findByName(name));
        if (employee.isPresent()) {
            employeeRepo.deleteByName(name);
            return "Employee Deleted Successfully";
        } else {

            throw new RuntimeException("Employee " + name + " Not Exist");
        }

    }

    public EmployeeDtoResponse updateEmployee(EmployeeDtoRequest employeeDto, int id) {

        Optional<Employee> employee = employeeRepo.findById(id);
        validateEmployee(employeeDto);
        if (employee.isPresent()) {


            checkNameIsDuplicate(employeeDto, id);

            Employee emp = modelMapper.map(employeeDto, Employee.class);
            emp.setId(id);
            return modelMapper.map(employeeRepo.save(emp), EmployeeDtoResponse.class);

        } else {

            throw new RuntimeException("Employee id = " + id + " Not Exist");
        }
    }

    public EmployeeDtoResponse getEmployeeById(int id) {

        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent()) {

            return modelMapper.map(employee.get(), EmployeeDtoResponse.class);
        } else {
            throw new RuntimeException("Employee id = " + id + " Not Exist");
        }

    }

    public void checkNameIsDuplicate(EmployeeDtoRequest employeeDto, int id) {

        for (Employee emp : employeeRepo.findAll()) {

            if (emp.getId() != id && emp.getName().equals(employeeDto.getName())) {
                throw new RuntimeException("Employee Name " + employeeDto.getName() + " Already Exist");
            }
        }
    }

    public void validateEmployee(EmployeeDtoRequest employeeDto) {

        employeeDataValidation.isValidAge(employeeDto.getAge());
        employeeDataValidation.isValidEmail(employeeDto.getEmail());
        employeeDataValidation.isValidSalary(employeeDto.getSalary());
    }

}
