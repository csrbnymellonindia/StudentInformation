package com.bezkoder.spring.jpa.h2.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.bezkoder.spring.jpa.h2.service.EmailSender;
import com.bezkoder.spring.jpa.h2.service.model.Email;

@RunWith(MockitoJUnitRunner.class)
public class TestControllerTest {

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private TestController testController;

    @Test
    public void emailTest() {
        Email email = new Email("sample@gmail.com", "TEST", "Test Mail");
        testController.emailTest(email);
    }
}
