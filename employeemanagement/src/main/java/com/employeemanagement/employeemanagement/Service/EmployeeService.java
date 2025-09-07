package com.employeemanagement.employeemanagement.Service;


import com.employeemanagement.employeemanagement.ConstantMessage.ErrorMsg;
import com.employeemanagement.employeemanagement.Entity.Address;
import com.employeemanagement.employeemanagement.Entity.Employee;
//import com.employeemanagement.employeemanagement.Exception.EmailNotFoundException;
import com.employeemanagement.employeemanagement.dto.Updatedto;
import com.employeemanagement.employeemanagement.Exception.AppException;
import com.employeemanagement.employeemanagement.Repository.EmployeeRepository;
import com.employeemanagement.employeemanagement.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService implements EmployeeInterface {
    @Autowired
    private EmployeeRepository employeeRepository;

      @Autowired
    EmailSender emailSender;
  @Override
    public ResponseEntity<ResponseApi> createEmployee(EmployeeDto employeeDto) {

        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeDto.getEmail());
        if (optionalEmployee.isPresent()) {
            throw new AppException(ErrorMsg.existingEmail,HttpStatus.BAD_REQUEST,employeeDto.getEmail());
        }
        //dto to entity

        Employee employee = new Employee();
        employee.setEmail(employeeDto.getEmail());
        employee.setName(employeeDto.getName());
        //address store into
        Address address=new Address();
        address.setCity(employeeDto.getAddressDto().getCity());
        address.setState(employeeDto.getAddressDto().getState());
        address.setPincode(employeeDto.getAddressDto().getPincode());
        address.setStreet(employeeDto.getAddressDto().getStreet());

        employee.setAddress(address);




       //username and password generate ho


      String username=employeeDto.getName().toLowerCase().replace(" ","")+"_"+(int)(Math.random()*1000);
      String password= UUID.randomUUID().toString().substring(0,8);
      employee.setUsername(username);
      employee.setPassword(password);
      Employee savedEmployee = employeeRepository.save(employee);
        String emailId=savedEmployee.getEmail();


        if(savedEmployee.getUsername()!=null && savedEmployee.getPassword()!=null){
              //mail msg
            String body="login details : "+"\n"+"username :- "+ savedEmployee.getUsername()+"\n"+"password :- "+savedEmployee.getPassword();
             String subject="login details ";
             String to=savedEmployee.getEmail();
             emailSender.sendEmail(to,subject,body);

        }
        else{
            throw new AppException("Internal Server Error No Username and Password Generated ",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //entity to dto

        EmployeeDto dtodetails = new EmployeeDto();
        dtodetails.setName(savedEmployee.getName());
        dtodetails.setEmail(savedEmployee.getEmail());
        AddressDto addressDto=new AddressDto();
        addressDto.setStreet(savedEmployee.getAddress().getStreet());
        addressDto.setCity(savedEmployee.getAddress().getCity());
        addressDto.setState(savedEmployee.getAddress().getState());
        addressDto.setPincode(savedEmployee.getAddress().getPincode());
        dtodetails.setAddressDto(addressDto);

        ResponseApi responseApi = new ResponseApi();
        responseApi.setError(false);
        responseApi.setMessage("Created !");
        HashMap<String, Object> meta = new HashMap<>();
        meta.put("Employee", dtodetails);
        responseApi.setMeta(meta);
        return new ResponseEntity<>(responseApi, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseApi> getAllEmployee() {
        ResponseApi responseApi = new ResponseApi();

        List<Employee> employeeList = employeeRepository.findAll();
        if (employeeList.isEmpty()) {
            throw new AppException(ErrorMsg.errorMessage,HttpStatus.NOT_FOUND);

        }
        //entity to dto

        List<EmployeeWithId> dtolist = employeeList.stream().map(employee -> {
            EmployeeWithId tempdto = new EmployeeWithId();
            tempdto.setEmail(employee.getEmail());
            tempdto.setName(employee.getName());
            tempdto.setId(employee.getId());
            AddressWithId addressdto=new AddressWithId();
            addressdto.setCity(employee.getAddress().getCity());
            addressdto.setStreet(employee.getAddress().getStreet());
            addressdto.setPincode(employee.getAddress().getPincode());
            addressdto.setState(employee.getAddress().getState());
            addressdto.setId(employee.getAddress().getId());
            tempdto.setAddressWithId(addressdto);
            return tempdto;
        }).toList();

        //return
        responseApi.setMessage("Employee List are found");
        HashMap<String, Object> meta = new HashMap<>();
        meta.put("employee  details", dtolist);
        responseApi.setMeta(meta);
        responseApi.setError(false);
        return new ResponseEntity<>(responseApi, HttpStatus.OK);
    }


@Override
    public ResponseEntity<ResponseApi> getEmployeeById(Long id) {
          ResponseApi responseApi=new ResponseApi();
         Optional<Employee>optionalEmployee= employeeRepository.findById(id);
         if(!optionalEmployee.isPresent()){
             throw new AppException(ErrorMsg.errorMessage,HttpStatus.NOT_FOUND,id);
         }
        //entity to dto
        EmployeeWithId employeedto=new EmployeeWithId();
         employeedto.setId(optionalEmployee.get().getId());
         employeedto.setName(optionalEmployee.get().getName());
         employeedto.setEmail(optionalEmployee.get().getEmail());
         //Address

        AddressWithId addressDto=new AddressWithId();
        addressDto.setPincode(optionalEmployee.get().getAddress().getPincode());
        addressDto.setCity(optionalEmployee.get().getAddress().getCity());
        addressDto.setStreet(optionalEmployee.get().getAddress().getStreet());
        addressDto.setState(optionalEmployee.get().getAddress().getState());
        addressDto.setId(optionalEmployee.get().getAddress().getId());

        employeedto.setAddressWithId(addressDto);

         //return
        responseApi.setMessage("Employee details");
        responseApi.setError(false);
        HashMap<String,Object>meta=new HashMap<>();
        meta.put("employee",employeedto);
        responseApi.setMeta(meta);
        return new ResponseEntity<>(responseApi,HttpStatus.OK);

    }


    @Override

    public ResponseEntity<ResponseApi> deleteEmployeById(Long id) {

       Optional<Employee>employeeOptional= employeeRepository.findById(id);
       ResponseApi responseApi=new ResponseApi();
       if(!employeeOptional.isPresent()){
           throw new AppException(ErrorMsg.errorMessage,HttpStatus.NOT_FOUND,id);
       }

       //entity to dto

        EmployeeWithId employeedto=new EmployeeWithId();
       employeedto.setId(employeeOptional.get().getId());
       employeedto.setName(employeeOptional.get().getName());
       employeedto.setEmail(employeeOptional.get().getEmail());
        //address
        AddressWithId addressdto=new AddressWithId();
        addressdto.setCity(employeeOptional.get().getAddress().getCity());
        addressdto.setStreet(employeeOptional.get().getAddress().getStreet());
        addressdto.setPincode(employeeOptional.get().getAddress().getPincode());
        addressdto.setState(employeeOptional.get().getAddress().getState());
        addressdto.setId(employeeOptional.get().getAddress().getId());

        employeedto.setAddressWithId(addressdto);

        //delete from db

        employeeRepository.deleteById(id);

        responseApi.setMessage("Deleted !");
        responseApi.setError(false);
        HashMap<String,Object>meta=new HashMap<>();
        meta.put("delete employee",employeedto);
        responseApi.setMeta(meta);
        return new ResponseEntity<>(responseApi,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseApi> updateEmploye(Long id, Updatedto updatedto) {

       Optional<Employee>employeeOptional= employeeRepository.findById(id);
       ResponseApi responseApi=new ResponseApi();
       if(!employeeOptional.isPresent()){
           throw new AppException(ErrorMsg.errorMessage,HttpStatus.NOT_FOUND,id);
       }

        // dto to entity
        Employee employee=employeeOptional.get();
       employee.setName(updatedto.getEmployeeDto().getName());
       employee.setEmail(updatedto.getEmployeeDto().getEmail());
       Address address=employee.getAddress();


           address.setCity(updatedto.getEmployeeDto().getAddressDto().getCity());
           address.setStreet(updatedto.getEmployeeDto().getAddressDto().getStreet());
           address.setState(updatedto.getEmployeeDto().getAddressDto().getState());
           address.setPincode(updatedto.getEmployeeDto().getAddressDto().getPincode());
           employee.setAddress(address);


       //entity update
    employeeRepository.save(employee);



    responseApi.setMeta(new HashMap<>());
    responseApi.setMessage("updated !");
    responseApi.setError(false);

    return new ResponseEntity<>(responseApi,HttpStatus.OK);

    }
}

