package com.bezkoder.spring.jpa.h2.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.bezkoder.spring.jpa.h2.model.Exam;
import com.bezkoder.spring.jpa.h2.model.Registration;
import com.bezkoder.spring.jpa.h2.model.Volunteer;
import com.bezkoder.spring.jpa.h2.repository.*;
import com.bezkoder.spring.jpa.h2.service.EmailSender;
import com.bezkoder.spring.jpa.h2.service.model.Email;
import com.bezkoder.spring.jpa.h2.service.model.Login;

@RunWith(MockitoJUnitRunner.class)
public class VolunteerControllerTest {
    @Mock
    private VolunteerRepository volunteerRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private EmailSender emailSender;

    @Test
    public void loginVolunteerTest(){
        Login login = new Login("sample@gmail.com", "password");
        VolunteerController volunteerController = new VolunteerController();
        volunteerController.loginVolunteer(login);

        when(volunteerRepository.findByEmail(login.getEmail())).thenReturn(Optional.empty());
        volunteerController.loginVolunteer(login);

        when(volunteerRepository.findByEmail(login.getEmail())).thenReturn(Optional.of(new Volunteer()));
        volunteerController.loginVolunteer(login);

        try{
            when(volunteerRepository.findByEmail(login.getEmail())).thenReturn(Optional.of(new Volunteer()));
            volunteerController.loginVolunteer(login);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void getProfileTest(){
        VolunteerController volunteerController = new VolunteerController();

        when(volunteerRepository.findByEmail(any())).thenReturn(Optional.empty());
        volunteerController.getProfile("sample@gmail.com");

        when(volunteerRepository.findByEmail(any())).thenReturn(Optional.of(new Volunteer()));
        volunteerController.getProfile("sample@gmail.com");

        try{
            when(volunteerRepository.findByEmail(any())).thenReturn(Optional.of(new Volunteer()));
            volunteerController.getProfile("sample@gmail.com");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void updateProfileTest(){
        VolunteerController volunteerController = new VolunteerController();

        when(volunteerRepository.findByEmail(any())).thenReturn(Optional.empty());
        volunteerController.updateProfile("sample@gmail.com", new Volunteer());

        when(volunteerRepository.findByEmail(any())).thenReturn(Optional.of(new Volunteer()));
        volunteerController.updateProfile("sample@gmail.com", new Volunteer());

        try{
            when(volunteerRepository.findByEmail(any())).thenThrow(new NullPointerException());
            volunteerController.updateProfile("sample@gmail.com", new Volunteer());
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void getVolunteeredExamsTest() {
        VolunteerController volunteerController = new VolunteerController();

        when(registrationRepository.findByVolunteerId(any())).thenReturn(new ArrayList<Registration>());
        volunteerController.getVolunteeredExams(1L);

        try{
            when(registrationRepository.findByVolunteerId(any())).thenThrow(new NullPointerException());
            volunteerController.getVolunteeredExams(1L);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void sendMailToParentTest() {
        VolunteerController volunteerController = new VolunteerController();

        when(volunteerRepository.findById(any())).thenReturn(Optional.empty());
        volunteerController.sendMailToParent(new Email("sample@gmail.com", "Test", "Test Mail"),1L);

        when(volunteerRepository.findById(any())).thenReturn(Optional.of(new Volunteer()));
        volunteerController.sendMailToParent(new Email("sample@gmail.com", "Test", "Test Mail"),1L);

        try{
            when(volunteerRepository.findById(any())).thenThrow(new NullPointerException());
            volunteerController.sendMailToParent(new Email("sample@gmail.com", "Test", "Test Mail"),1L);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}