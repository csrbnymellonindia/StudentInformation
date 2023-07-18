package com.bezkoder.spring.jpa.h2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.h2.service.EmailSender;
import com.bezkoder.spring.jpa.h2.service.model.Email;

@CrossOrigin
@RestController
@RequestMapping("/test")
public class TestController {
    
    @Autowired
    private EmailSender senderService;

    @GetMapping("/email")
    public String emailTest(@RequestBody Email email)
    {
        senderService.sendEmail(
            email.getToEmail(),
            email.getSubject(),
            email.getBody()
        );
        return "Email Sent";
    }
}
