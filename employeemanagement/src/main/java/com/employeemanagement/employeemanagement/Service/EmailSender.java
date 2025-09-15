package com.employeemanagement.employeemanagement.Service;

import com.employeemanagement.employeemanagement.Repository.EmployeeRepository;
import com.employeemanagement.employeemanagement.dto.EmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

   @Autowired
   private JavaMailSender  javaMailSender;

   public void sendEmail(String to,String subject,String body){
       SimpleMailMessage message=new SimpleMailMessage();
       message.setTo(to);
       message.setSubject(subject);
       message.setText(body);
      javaMailSender.send(message);
   }
    public void sendOtp(String to,String subject,String body){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
    }
}
