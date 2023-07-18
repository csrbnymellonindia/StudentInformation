package com.bezkoder.spring.jpa.h2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.h2.model.Admin;
import com.bezkoder.spring.jpa.h2.model.Exam;
import com.bezkoder.spring.jpa.h2.model.Registration;
import com.bezkoder.spring.jpa.h2.model.Student;
import com.bezkoder.spring.jpa.h2.model.Volunteer;
import com.bezkoder.spring.jpa.h2.repository.AdminRepository;
import com.bezkoder.spring.jpa.h2.repository.ExamRepository;
import com.bezkoder.spring.jpa.h2.repository.RegistrationRepository;
import com.bezkoder.spring.jpa.h2.repository.StudentRepository;
import com.bezkoder.spring.jpa.h2.repository.VolunteerRepository;
import com.bezkoder.spring.jpa.h2.service.EmailSender;
import com.bezkoder.spring.jpa.h2.service.model.Email;
import com.bezkoder.spring.jpa.h2.service.model.ListofStudent;

import jakarta.mail.MessagingException;

@CrossOrigin
@RestController
@RequestMapping("/exams")
public class ExamController {
    @Autowired
    RegistrationRepository registrationRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ExamRepository examRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    VolunteerRepository volunteerRepository;
    @Autowired
    EmailSender emailSender;

    @GetMapping("/registered/{examid}")
    public ResponseEntity<Object> registredStudents(@PathVariable("examid") Long id)
    {
        try{
            List<Student> studentsInExam = registrationRepository.findByExamId(id)
            .stream()
            .map(Registration::getStudent)
            .collect(Collectors.toList());
            return new ResponseEntity<>(studentsInExam, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/unregistered/{examid}")
    public ResponseEntity<Object> unregistredStudents(@PathVariable("examid") Long id)
    {
        try{
            List<Student> allStudents = studentRepository.findAll();
            List<Student> studentsInExam = registrationRepository.findByExamId(id)
            .stream()
            .map(Registration::getStudent)
            .collect(Collectors.toList());
            allStudents.removeAll(studentsInExam);
            return new ResponseEntity<>(allStudents, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getexam/{id}")
    public ResponseEntity<Object> getParticularExam(@PathVariable("id") long id) {
        try {
            Optional<Exam> examData = examRepository.findById(id);
            if (examData.isPresent()) {
                return new ResponseEntity<>(examData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Exam Not Found",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sendemailtoparent")
    public ResponseEntity<Object> sendMailToParent(@RequestBody ListofStudent listofStudent,@RequestHeader("type") String type, @RequestHeader("id") Long id)
    {
        try{
            if("admin".equals(type))
            {
                Optional<Admin> admin=adminRepository.findById(id);
                if(admin.isPresent())
                {
                    Admin adminData = admin.get();
                    List<CompletableFuture<Void>> futures=new ArrayList<>();
                    for (Long temp : listofStudent.getArr()) {
                        Optional<Student> student = studentRepository.findById(temp);
                        if(student.isPresent())
                        {
                            Student studentData=student.get();
                            CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
                            try {
                                emailSender.sendEmailHTMLAdminToParent(studentData.getParentemail(), listofStudent.getSubject(),listofStudent.getBody(), adminData.getPhone(),adminData.getEmail());
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            }
                            
                            });
                            futures.add(future);
                        }
                    }            
                    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
                    return new ResponseEntity<>("Email Sent", HttpStatus.OK);
                }
                else
                {
                    return new ResponseEntity<>("Admin Not found", HttpStatus.NOT_FOUND);
                }
            }
            else
            {
                Optional<Volunteer> volunteer=volunteerRepository.findById(id);
                if(volunteer.isPresent())
                {
                    Volunteer volunteerData = volunteer.get();
                    List<CompletableFuture<Void>> futures=new ArrayList<>();
                    for (Long temp : listofStudent.getArr()) {
                        Optional<Student> student = studentRepository.findById(temp);
                        if(student.isPresent())
                        {
                            Student studentData=student.get();
                            CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
                            try {
                                emailSender.sendEmailHTMLVolunteerToParent(studentData.getParentemail(), listofStudent.getSubject(),listofStudent.getBody(), volunteerData.getPhone(),volunteerData.getEmail(),volunteerData.getName());
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            }
                            
                            });
                            futures.add(future);
                        }
                    }            
                    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
                    return new ResponseEntity<>("Email Sent", HttpStatus.OK);
                }
                else
                {
                    return new ResponseEntity<>("Admin Not found", HttpStatus.NOT_FOUND);
                }
            }
        }
        catch (Exception e){
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
