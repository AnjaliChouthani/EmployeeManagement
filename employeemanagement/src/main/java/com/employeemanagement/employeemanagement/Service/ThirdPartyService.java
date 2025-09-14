package com.employeemanagement.employeemanagement.Service;

import com.employeemanagement.employeemanagement.Exception.AppException;
import com.employeemanagement.employeemanagement.dto.GeneratedOtp;
import com.employeemanagement.employeemanagement.dto.MobileDto;
import com.employeemanagement.employeemanagement.dto.MobileOtpDto;
import com.employeemanagement.employeemanagement.dto.OtpVerificationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ThirdPartyService {
    @Value("${ekyc.url}")
    private String url;
    @Value("${ekyc.mobile.verify.otp}")
    private String mobileVerify;
    @Value("${ekyc.secreteKey}")
    private String secretKey;
    @Value("${ekyc.apikey}")
    private String apiKey;

    public  static final Logger logger= LoggerFactory.getLogger(ThirdPartyService.class);
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> callThirdApi(MobileDto mobileDto) {


        GeneratedOtp generatedOtp=new GeneratedOtp();
        generatedOtp.setApiKey(apiKey);
        generatedOtp.setSecretKey(secretKey);
        generatedOtp.setMobileNumber(mobileDto.getMobileNumber());
           HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//           HashMap<String,String>body=new HashMap<>();
//            body.put("mobileNumber",mobile);
//     body.put("apiKey",apiKey);
//     body.put("secretKey",secretKey);
        HttpEntity<GeneratedOtp>request=new HttpEntity<>(generatedOtp,httpHeaders);
       String response= restTemplate.postForObject(url,request,String.class);
       logger.info("mobile number : {}", mobileDto.getMobileNumber(),"sent otp successfully ");
     return new ResponseEntity<>(response, HttpStatus.OK);
    }
   public ResponseEntity<String> verifyThirdApi(MobileOtpDto mobileDto) {
           String mobile=mobileDto.getMobile();
           String otp=mobileDto.getOtp();
       OtpVerificationDto otpVerificationDto=new OtpVerificationDto();
       otpVerificationDto.setApiKey(apiKey);
       otpVerificationDto.setSecretKey(secretKey);
       otpVerificationDto.setMobileNumber(mobileDto.getMobile());
       otpVerificationDto.setOtp(mobileDto.getOtp());
//           Map<String,String>body=new HashMap<>();
//           body.put("mobileNumber",mobile);
//           body.put("apiKey",apiKey);
//           body.put("secretKey",secretKey);
//           body.put("otp",otp);
           HttpHeaders httpHeaders=new HttpHeaders();
           httpHeaders.setContentType(MediaType.APPLICATION_JSON);
           HttpEntity<OtpVerificationDto>request=new HttpEntity<>(otpVerificationDto,httpHeaders);
        // raw string
         String verifyOtp= restTemplate.postForObject(mobileVerify,request,String.class);
           ObjectMapper objectMapper=new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(verifyOtp);

            boolean status = jsonNode.get("status").asBoolean();
            String message = jsonNode.get("message").asText();
            if(!status){
                return new ResponseEntity<>("Expired Otp",HttpStatus.BAD_REQUEST);
            }
        }
        catch (JsonProcessingException e){
            throw new AppException("invalid json otp ",HttpStatus.BAD_REQUEST);
        }
        logger.info("mobile number : {} otp: {}",mobileDto.getMobile(),mobileDto.getOtp());
        return new ResponseEntity<>("Successfully verified!",HttpStatus.OK);
    }
}
