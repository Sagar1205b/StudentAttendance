package com.sahana.StudentAttendance.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public  void sendEmail(String toEmail,String subject,String body){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("sbadammanavar.k7it@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        mailSender.send(mailMessage);
        System.out.println("Mail sent successfully to "+toEmail);

    }
}
