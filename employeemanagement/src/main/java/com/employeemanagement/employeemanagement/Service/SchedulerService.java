//package com.employeemanagement.employeemanagement.Service;
//
//
//import com.employeemanagement.employeemanagement.Entity.Employee;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.scheduling.annotation.Scheduled;
//
//import org.springframework.stereotype.Service;
//
//
//
//import java.util.List;
//
//@Service
//public class SchedulerService {
//    @Autowired
//    private EmailSender emailSender;
//    @Autowired
//            private CacheDetails cacheDetails;
//
//    Logger logger= LoggerFactory.getLogger(SchedulerService.class);
//    @Scheduled(cron = "0 *\5 10-15 * * *",zone = "Asia/Kolkata")
//    public void schedulerCall(){
//        List<Employee>employee=cacheDetails.getEmployee();
//        logger.info("get now data from the cache");
//        if(employee!=null) {
//            employee.stream().forEach(e -> {
//                String to = e.getEmail();
//                String subject = "Regarding Motivation Quote's";
//                String body = "Dear " + e.getName() + ", \n" +
//                        "\"Success is the sum of small efforts, repeated day in and day out.\"  \n" +
//                        "\n" +
//                        "Keep up the great work! \n" +
//                        "–Admin Team";
//                try {
//                    emailSender.sendEmail(to, subject, body);
//                    logger.info("mail send emailId {}", e.getEmail());
//                }
//                catch (Exception ex){
//                    logger.warn("not send mail at {}",e.getName(),ex);
//                }
//            });
//        }
//    }
//}
