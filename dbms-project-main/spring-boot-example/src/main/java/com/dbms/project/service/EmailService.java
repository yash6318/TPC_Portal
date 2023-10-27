package com.dbms.project.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendEmail(List<String> names, List<String> mailIds, String companyName, String roleName) {

        for(int i = 0; i < names.size(); i++){
            SimpleMailMessage message = new SimpleMailMessage();
            System.out.println(mailIds.get(i));
            message.setTo(mailIds.get(i));
            message.setSubject("Congratulations You Have Been Selected for the interview of " + roleName + "role.");
            message.setText("Greetings from " + companyName + "\n" + "Hi " + names.get(i) + ", You have been Selected for the interviews.\n \n Regards.\n" + companyName + "\n");
            javaMailSender.send(message);
        }

    }

}
