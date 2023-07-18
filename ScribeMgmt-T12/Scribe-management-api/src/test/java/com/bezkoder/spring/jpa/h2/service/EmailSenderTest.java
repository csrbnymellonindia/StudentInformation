package com.bezkoder.spring.jpa.h2.service;

import jakarta.mail.MessagingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class EmailSenderTest {
    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailSender emailSender;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendEmailTest(){
        emailSender.sendEmail("sample@gmail.com","TEST MAIL","This is test mail");
    }

    @Test
    public void sendEmailHTMLTest() {
        try {
            emailSender.sendEmailHTMLToVoluntter("sample@gmail.com", "Invitation to Volunteer", new Date(1273908712), "http://localhost:4200/user/volunteer/checkin?examId=1&volId=3");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
