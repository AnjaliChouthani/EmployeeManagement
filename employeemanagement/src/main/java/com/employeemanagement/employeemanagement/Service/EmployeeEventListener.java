//package com.employeemanagement.employeemanagement.Service;
//
//import com.employeemanagement.employeemanagement.Entity.Employee;
//import com.employeemanagement.employeemanagement.Event.EmployeeCreatedEvent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class EmployeeEventListener {
//    @Autowired
//    private EmailSender emailSender;
//Logger logger= LoggerFactory.getLogger(EmployeeEventListener.class);
//@EventListener
//    public void handleEmailSender(EmployeeCreatedEvent employeeCreatedEvent){
//        Employee employee=employeeCreatedEvent.getEmployee();
//
//         String to=employee.getEmail();
//         String body="Welcome to Lincpay , \n "+"Dear "+ employee.getName()+"\n this is your username and password "+ employee.getUsername()+
//                 ", "+employee.getPassword();
//         String subject="Registered Successfully ";
//
//         logger.info("send email at {}{}{}",to,subject,body);
//         emailSender.sendEmail(to,subject,body);
//
//    }
//}

