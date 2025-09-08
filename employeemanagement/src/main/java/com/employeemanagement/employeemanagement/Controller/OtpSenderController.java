package com.employeemanagement.employeemanagement.Controller;


import com.employeemanagement.employeemanagement.Repository.EmployeeRepository;
import com.employeemanagement.employeemanagement.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otpController")
public class OtpSenderController {
    @Autowired
   private OtpService otpService;

    @PostMapping("/sendOtpByEmail")
    public ResponseEntity<String> otpSender(@RequestParam String email){
        otpService.otpSender(email);
 return new ResponseEntity<>("send otp successfully in email "+ email, HttpStatus.OK);
    }
}
