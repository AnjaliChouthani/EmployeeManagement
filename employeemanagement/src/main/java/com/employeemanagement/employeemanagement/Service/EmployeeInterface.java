package com.employeemanagement.employeemanagement.Service;

import com.employeemanagement.employeemanagement.dto.EmployeeDto;
import com.employeemanagement.employeemanagement.dto.ResponseApi;
import com.employeemanagement.employeemanagement.dto.Updatedto;
import org.springframework.http.ResponseEntity;

public interface EmployeeInterface {

    public ResponseEntity<ResponseApi> createEmployee(EmployeeDto employeeDto);

    public ResponseEntity<ResponseApi> getAllEmployee();
    public ResponseEntity<ResponseApi> getEmployeeById(Long id);
    public ResponseEntity<ResponseApi> deleteEmployeById(Long id);
    public ResponseEntity<ResponseApi> updateEmploye(Long id, Updatedto updatedto);
}
