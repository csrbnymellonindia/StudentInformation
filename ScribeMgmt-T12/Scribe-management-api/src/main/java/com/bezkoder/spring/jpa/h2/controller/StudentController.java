package com.bezkoder.spring.jpa.h2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.h2.model.Exam;
import com.bezkoder.spring.jpa.h2.model.Registration;
import com.bezkoder.spring.jpa.h2.model.Student;
import com.bezkoder.spring.jpa.h2.repository.ExamRepository;
import com.bezkoder.spring.jpa.h2.repository.RegistrationRepository;
import com.bezkoder.spring.jpa.h2.repository.StudentRepository;

@CrossOrigin
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    ExamRepository examRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    RegistrationRepository registrationRepository;
    
    @GetMapping("/getstudents")
    public ResponseEntity<Object> getStudents(@RequestHeader("user") String type) {
        try {
            if("admin".equals(type))
            {
                List<Student> students = new ArrayList<Student>();
                studentRepository.findAll().forEach(students::add);
                if (students.isEmpty()) {
                    return new ResponseEntity<>("No records", HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(students, HttpStatus.OK);
            }
            else
            {
                long number = Long.parseLong(type);
                List<Student> studentsInVolunteer = registrationRepository.findByVolunteerId(number)
                    .stream()
                    .map(Registration::getStudent)
                    .collect(Collectors.toList());
                return new ResponseEntity<>(studentsInVolunteer, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
