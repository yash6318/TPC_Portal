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


    public void sendEmail(List<String> names, List<String> mailIds, String companyName, String roleName, String customText, String customSub) {

        for(int i = 0; i < names.size(); i++){
            SimpleMailMessage message = new SimpleMailMessage();
//            System.out.println(mailIds.get(i));
            message.setTo(mailIds.get(i));
            message.setSubject(customSub);
            message.setText(customText);

            // add a particular text which will always be there notifying about company ID and role

            javaMailSender.send(message);
        }

    }

}
