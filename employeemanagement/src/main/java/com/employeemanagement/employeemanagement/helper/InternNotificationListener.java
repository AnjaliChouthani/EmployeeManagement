package com.employeemanagement.employeemanagement.helper;

import com.employeemanagement.employeemanagement.Service.EmailSender;
import com.employeemanagement.employeemanagement.dto.InternDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.PGConnection;
import org.postgresql.PGNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

    @Component
    public class InternNotificationListener {

        private final DataSource dataSource;
        private final EmailSender emailSender; // your existing service
        private final ObjectMapper objectMapper = new ObjectMapper();
        Logger logger= LoggerFactory.getLogger(InternNotificationListener.class);

        public InternNotificationListener(DataSource dataSource, EmailSender emailSender) {
            this.dataSource = dataSource;
            this.emailSender = emailSender;
        }
        @PostConstruct
        public void start() {
            new Thread(this::listen).start();
        }
        private void listen() {
            try (Connection conn = dataSource.getConnection()) {
                PGConnection pgConn = conn.unwrap(PGConnection.class);

                try (Statement stmt = conn.createStatement()) {
                    stmt.execute("LISTEN new_intern_channel");
                    logger.info("✅ Listening for new interns...");
                }
                while (true) {
                    PGNotification[] notifications = pgConn.getNotifications(5000);
                    if (notifications != null) {
                        for (PGNotification notification : notifications) {
                            String json = notification.getParameter();
                            InternDto dto = objectMapper.readValue(json, InternDto.class);


                            String subject="";
                            String body="";
                           if("INSERT".equalsIgnoreCase(dto.getOperation())){
                                subject="Welcome to LincPay! ";
                                body="Hi "+dto.getName()+",\nWelcome to LincPay";
                           }
                           else if("UPDATE".equalsIgnoreCase(dto.getOperation())){
                               subject="profile updated ";
                               body="Hi "+dto.getName()+",\n Your Profule has been updated successfully";
                           }
                           emailSender.sendEmail(dto.getEmail(),subject,body);
                           logger.info("Email sent to "+dto.getEmail()+ "for operation "+ dto.getOperation());

                        }
                    }
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
