package com.gizasystems;

import com.gizasystems.dto.EmployeeDtoRequest;
import com.gizasystems.dto.EmployeeDtoResponse;
import com.gizasystems.entity.Employee;
import com.gizasystems.repository.EmployeeRepo;
import com.gizasystems.service.EmployeeDataValidation;
import com.gizasystems.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeDataValidation employeeDataValidation;
    @Mock
    EmployeeRepo employeeRepo;
    @Spy
    @InjectMocks
    EmployeeService employeeService;

    @Test
    public void getEmployeeById_IdIsExist() {
        Employee employee = new Employee(1, "ahmed", 26.0, "yassin@gmail.com", 1234.0);

        doReturn(Optional.of(employee)).when(employeeRepo).findById(anyInt());

        assertNotNull(employeeService.getEmployeeById(anyInt()));

    }

    @Test
    public void getEmployeeById_IdNotExist() {

        doReturn(Optional.empty()).when(employeeRepo).findById(anyInt());

        assertThrows(RuntimeException.class, () -> {

            employeeService.getEmployeeById(anyInt());

        });


    }


    @Test
    public void getEmployeeByName_NameIsExist() {
        Employee employee = new Employee(1, "ahmed", 26.0, "yassin@gmail.com", 1234.0);
        doReturn(employee).when(employeeRepo).findByName(anyString());


        assertNotNull(employeeService.getEmployeeByName(anyString()));

    }

    @Test
    public void getEmployeeByName_NameNotExist() {

        doReturn(null).when(employeeRepo).findByName(anyString());

        assertThrows(RuntimeException.class, () -> {

            employeeService.getEmployeeByName(anyString());

        });


    }

    @Test
    public void getAllEmployeesTest() {

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "ahmed", 26.0, "yassin@gmail.com", 1234.0));
        employeeList.add(new Employee(2, "yassin", 26.0, "yassin@gmail.com", 1234.0));


        doReturn(employeeList).when(employeeRepo).findAll();
        List<EmployeeDtoResponse> employeesDtoResponses = employeeService.getEmployees();
        assertTrue(employeesDtoResponses.size() >= 0);
    }

    @Test
    public void deleteEmployeeByName_NameIsExist() {

        Employee employee = new Employee(1, "ahmed", 26.0, "yassin@gmail.com", 1234.0);
        doReturn(employee).when(employeeRepo).findByName(anyString());
        doReturn(1).when(employeeRepo).deleteByName(anyString());

        assertTrue(employeeService.deleteEmployeeByName(anyString()).contains("Deleted"));
    }

    @Test
    public void deleteEmployeeByName_NameNotExist() {


        doReturn(null).when(employeeRepo).findByName(anyString());

        assertThrows(RuntimeException.class, () -> {
            employeeService.deleteEmployeeByName(anyString());

        });
    }

    @Test
    public void createEmployeeTest_NameIsDuplicate() {
        Employee employee = new Employee(1, "ahmed", 26.0, "yassin@gmail.com", 1234.0);
        doNothing().when(employeeService).validateEmployee(any(EmployeeDtoRequest.class));

        doReturn(employee).when(employeeRepo).findByName(anyString());

        assertThrows(RuntimeException.class, () -> {
            employeeService.saveEmployee(new EmployeeDtoRequest("ahmed", 12.5, "yassin@gmail.com", 123.5));

        });
    }

    @Test
    public void createEmployeeTest_NameIsUnique() {
        Employee employee = new Employee(1, "ahmed", 26.0, "yassingmail.com", 1234.0);
        doNothing().when(employeeService).validateEmployee(any(EmployeeDtoRequest.class));

        doReturn(null).when(employeeRepo).findByName(anyString());

        doReturn(employee).when(employeeRepo).save(any(Employee.class));
        EmployeeDtoResponse employeeDtoResponse = employeeService.saveEmployee(new EmployeeDtoRequest("ahmed", 12.5, "yassin@gmail.com", 123.5));
        assertNotNull(employeeDtoResponse);
    }

    @Test
    public void updateEmployeeTest_IdNotExist() {

        doReturn(null).when(employeeRepo).findById(anyInt());

        assertThrows(RuntimeException.class, () -> {

            employeeService.updateEmployee(new EmployeeDtoRequest("ahmed", 12.5, "yassin@gmail.com", 123.5), 1);
        });
    }


    @Test
    public void updateEmployeeTest_IdExist_And_NameIsUniqe() {
        Employee employee = new Employee(1, "ahmed", 26.0, "yassin@gmail.com", 1234.0);

        doReturn(Optional.of(employee)).when(employeeRepo).findById(anyInt());
        doNothing().when(employeeService).validateEmployee(any(EmployeeDtoRequest.class));
        doReturn(employee).when(employeeRepo).save(any(Employee.class));
        EmployeeDtoResponse employeeDtoResponse = employeeService.updateEmployee(new EmployeeDtoRequest("ahmed", 14, "yassin@gmail.com", 789.5), 1);
        assertNotNull(employeeDtoResponse);
    }

    @Test

    public void updateEmployeeTest_IdExist_And_NameIsDuplicate() {
        Employee employee = new Employee(1, "ahmed", 26.0, "yassin@gmail.com", 1234.0);

        doReturn(Optional.of(employee)).when(employeeRepo).findById(anyInt());
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "ahmed", 26.0, "yassin@gmail.com", 1234.0));
        employeeList.add(new Employee(2, "yassin", 26.0, "yassin@gmail.com", 1234.0));

        doReturn(employeeList).when(employeeRepo).findAll();
        assertThrows(RuntimeException.class, () -> {

            employeeService.updateEmployee(new EmployeeDtoRequest("yassin", 12.5, "yassin@gmail.com", 123.5), 1);
        });

    }
}
