package com.bezkoder.spring.jpa.h2.controller;

import com.bezkoder.spring.jpa.h2.repository.*;
import com.bezkoder.spring.jpa.h2.service.HashPassword;
import com.bezkoder.spring.jpa.h2.service.model.Login;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.bezkoder.spring.jpa.h2.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {
    @Mock
    private AdminRepository adminRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private ExamRepository examRepository;
    @Mock
    private RegistrationRepository registrationRepository;
    @InjectMocks
    private AdminController adminController;

    @Test
    public void loginadminTest(){
        Login login = new Login("sample@gmail.com","password");
        Admin admin = new Admin();
        admin.setPassword(HashPassword.hashedPassword("password"));
        Optional<Admin> adminData = Optional.of(admin);
        when(adminRepository.findByEmail(anyString())).thenReturn(adminData);
        adminController.loginadmin(login);

        login = new Login("sample@gmail.com","pass");
        adminController.loginadmin(login);
        
        adminData = Optional.empty();
        when(adminRepository.findByEmail(anyString())).thenReturn(adminData);
        adminController.loginadmin(login);

        try{
            when(adminRepository.findByEmail(anyString())).thenThrow(new NullPointerException());
            adminController.loginadmin(login);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void getStudentsTest(){
        when(studentRepository.findAll()).thenReturn(new ArrayList<Student>());
        adminController.getStudents();

        List<Student> students = new ArrayList<Student>();
        students.add(new Student("name","address","father@gmail.com","father","mother","roll1","1234567890"));
        when(studentRepository.findAll()).thenReturn(students);
        adminController.getStudents();

        try{
            when(studentRepository.findAll()).thenThrow(new NullPointerException());
            adminController.getStudents();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void getParticularStudentTest(){
        when(studentRepository.findById(any())).thenReturn(Optional.empty());
        adminController.getParticularStudent(1);

        Student student = new Student("name","address","father@gmail.com","father","mother","roll1","1234567890");
        Optional <Student> studentData = Optional.of(student);
        when(studentRepository.findById(any())).thenReturn(studentData);
        adminController.getParticularStudent(1);

        try{
            when(studentRepository.findById(any())).thenThrow(new NullPointerException());
            adminController.getParticularStudent(1);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void addExamTest(){
        when(examRepository.save(any())).thenReturn(new Exam());
        adminController.addExam(new Exam());

        try{
            when(examRepository.save(any())).thenThrow(new NullPointerException());
            adminController.addExam(new Exam());
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void getExamsTest(){
        when(examRepository.findAll()).thenReturn(new ArrayList<Exam>());
        adminController.getExams();

        List<Exam> exams = new ArrayList<Exam>();
        exams.add(new Exam());
        when(examRepository.findAll()).thenReturn(exams);
        adminController.getExams();

        try{
            when(examRepository.findAll()).thenThrow(new NullPointerException());
            adminController.getExams();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void getProfileTest(){
        when(adminRepository.findById(any())).thenReturn(Optional.empty());
        adminController.getProfile("sample@gmail.com");

        Admin admin = new Admin();
        Optional <Admin> adminData = Optional.of(admin);
        when(adminRepository.findById(any())).thenReturn(adminData);
        adminController.getProfile("sample@gmail.com");

        try{
            when(adminRepository.findById(any())).thenThrow(new NullPointerException());
            adminController.getProfile("sample@gmail.com");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void updateProfileTest(){
        when(adminRepository.findByEmail(any())).thenReturn(Optional.empty());
        adminController.updateProfile("sample@gmail.com",new Admin());
        
        Admin admin = new Admin();
        Optional <Admin> adminData = Optional.of(admin);
        when(adminRepository.findByEmail(any())).thenReturn(adminData);
        adminController.updateProfile("sample@gmail.com",new Admin());

        try{
            when(adminRepository.findByEmail(any())).thenThrow(new NullPointerException());
            adminController.updateProfile("sample@gmail.com",new Admin());
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    @Test
    public void getRegistrationExamTest(){
        List<Registration> registrations = new ArrayList<Registration>();
        registrations.add(new Registration());
        when(registrationRepository.findByExamId(any())).thenReturn(registrations);
        adminController.getRegistrationExam(1L);

        try{
            when(registrationRepository.findByExamId(any())).thenThrow(new NullPointerException());
            adminController.getRegistrationExam(1L);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
