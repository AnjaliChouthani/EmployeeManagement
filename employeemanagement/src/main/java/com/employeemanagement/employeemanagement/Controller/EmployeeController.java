package com.employeemanagement.employeemanagement.Controller;


import com.employeemanagement.employeemanagement.Service.EmployeeInterface;
import com.employeemanagement.employeemanagement.dto.EmployeeDto;
import com.employeemanagement.employeemanagement.dto.ResponseApi;
import com.employeemanagement.employeemanagement.dto.Updatedto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employeeCon")
public class EmployeeController {
    @Autowired
    private EmployeeInterface employeeService;
    @PostMapping("/addEmployee")
     @PreAuthorize("hasAnyRole('Admin','Manager')")
    public ResponseEntity<ResponseApi> createEmployee(@RequestBody EmployeeDto employeeDto){
       return employeeService.createEmployee(employeeDto);
    }
     @PreAuthorize("hasAnyRole('Admin','Manager')")
    @GetMapping("/getAllEmployee")
    public ResponseEntity<ResponseApi> getAllEmployee(){
        return employeeService.getAllEmployee();
    }
    @PreAuthorize("hasAnyRole('Admin','Manager')")
    @GetMapping("/getById/{id}")
    public ResponseEntity<ResponseApi>getEmployeeById(@PathVariable Long id){
        return employeeService.getEmployeeById(id);
    }
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<ResponseApi>deleteEmployeeById(@PathVariable Long id){
        return employeeService.deleteEmployeById(id);
    }
     @PreAuthorize("hasAnyRole('Admin','Manager')")
    @PutMapping("/updateById/{id}")
    public ResponseEntity<ResponseApi>updateEmploye(@PathVariable Long id, @RequestBody Updatedto updatedto){
         return employeeService.updateEmploye(id,updatedto);
    }
}
