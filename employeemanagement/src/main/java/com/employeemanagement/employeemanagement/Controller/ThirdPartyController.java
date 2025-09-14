package com.employeemanagement.employeemanagement.Controller;


import com.employeemanagement.employeemanagement.Exception.AppException;
import com.employeemanagement.employeemanagement.Service.ThirdPartyService;
import com.employeemanagement.employeemanagement.dto.MobileDto;
import com.employeemanagement.employeemanagement.dto.MobileOtpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calling_third_party")
public class ThirdPartyController {

        @Autowired
        private ThirdPartyService thirdPartyService;

        @PostMapping("/call")
       public ResponseEntity<String>callThirdApi(@RequestBody MobileDto mobileDto){
            return thirdPartyService.callThirdApi(mobileDto);
        }
        @PostMapping("/verifyOtp")
    public ResponseEntity<String>verifyThirdApi(@RequestBody MobileOtpDto mobileDto){
            return thirdPartyService.verifyThirdApi(mobileDto);
        }


}
