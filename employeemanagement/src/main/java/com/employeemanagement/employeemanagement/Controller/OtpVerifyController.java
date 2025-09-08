package com.employeemanagement.employeemanagement.Controller;


import com.employeemanagement.employeemanagement.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verificationController")
public class OtpVerifyController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/checkOtp")
    public ResponseEntity<String> otpVerify(@RequestParam String email,@RequestParam String otp){
          return   otpService.otpChecker(email,otp);
    }
}
