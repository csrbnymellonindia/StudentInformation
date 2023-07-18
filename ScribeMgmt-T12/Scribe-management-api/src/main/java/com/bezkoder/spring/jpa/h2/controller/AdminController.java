package com.bezkoder.spring.jpa.h2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.h2.model.Admin;
import com.bezkoder.spring.jpa.h2.model.Exam;
import com.bezkoder.spring.jpa.h2.model.Student;
import com.bezkoder.spring.jpa.h2.model.Volunteer;
import com.bezkoder.spring.jpa.h2.repository.AdminRepository;
import com.bezkoder.spring.jpa.h2.repository.ExamRepository;
import com.bezkoder.spring.jpa.h2.repository.RegistrationRepository;
import com.bezkoder.spring.jpa.h2.repository.StudentRepository;
import com.bezkoder.spring.jpa.h2.repository.VolunteerRepository;
import com.bezkoder.spring.jpa.h2.service.EmailSender;
import com.bezkoder.spring.jpa.h2.service.HashPassword;
import com.bezkoder.spring.jpa.h2.service.model.Email;
import com.bezkoder.spring.jpa.h2.service.model.Login;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ExamRepository examRepository;
    @Autowired
    RegistrationRepository registrationRepository;
    @Autowired
    EmailSender emailSender;
    @Autowired
    VolunteerRepository volunteerRepository;

    @PostMapping("/login")
    public ResponseEntity<Object> loginadmin(@RequestBody Login login) {
        try {
            Optional<Admin> adminData = adminRepository.findByEmail(login.getEmail());
            if (adminData.isPresent()) {
                Admin admin = adminData.get();
                boolean flag = HashPassword.checkPassword(login.getPassword(), admin.getPassword());
                if (flag) {
                    return new ResponseEntity<>(admin, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addadmin")
    public ResponseEntity<Object> addAdmin(@Valid @RequestBody Admin admin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        try {
            Optional<Admin> adminData = adminRepository.findByEmail(admin.getEmail());
            if (adminData.isPresent()) {
                return new ResponseEntity<>("Email is already registered", HttpStatus.CONFLICT);
            }
            String hash = HashPassword.hashedPassword(admin.getPassword());
            Admin _admin = adminRepository.save(new Admin(admin.getName(), admin.getPhone(), admin.getEmail(), hash, admin.getAddress()));
            return new ResponseEntity<>(_admin, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addstudent")
    public ResponseEntity<Object> addStudent(@Valid @RequestBody Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        try {
            Student _student = studentRepository.save(new Student(student.getName(), student.getAddress(),
                    student.getParentemail(), student.getFathername(), student.getMothername(),student.getRollno(), student.getPhone()));
            return new ResponseEntity<>(_student, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getstudents")
    public ResponseEntity<Object> getStudents() {
        try {
            List<Student> students = new ArrayList<Student>();
            studentRepository.findAll().forEach(students::add);

            if (students.isEmpty()) {
                return new ResponseEntity<>("No records", HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatestudent/{id}")
    public ResponseEntity<Object> updateStudent(@Valid @RequestBody Student student,@PathVariable("id") Long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        try {
             Optional<Student> studentData = studentRepository.findById(id);
            if (studentData.isPresent()) {
                Student _student = studentData.get();
                    _student.setAddress(student.getAddress());
                    _student.setFathername(student.getFathername());
                    _student.setMothername(student.getMothername());
                    _student.setName(student.getName());
                    _student.setParentemail(student.getParentemail());
                    _student.setRollno(student.getRollno());
                    _student.setPhone(student.getPhone());
                    return new ResponseEntity<>(studentRepository.save(_student), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getstudent/{id}")
    public ResponseEntity<Object> getParticularStudent(@PathVariable("id") long id) {
        try {
            Optional<Student> studentData = studentRepository.findById(id);
            if (studentData.isPresent()) {
                return new ResponseEntity<>(studentData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Student Not Found",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addexam")
    public ResponseEntity<Object> addExam(@RequestBody Exam exam) {
        try {
            Exam _exam = examRepository.save(new Exam(exam.getName(),exam.getDesc(),exam.getAddress(),exam.getDate(),exam.getCity(),exam.getState(),exam.getPostalcode()));
            return new ResponseEntity<>(_exam, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getexams")
    public ResponseEntity<Object> getExams() {
        try {
            List<Exam> exams = new ArrayList<Exam>();
            examRepository.findAll().forEach(exams::add);

            if (exams.isEmpty()) {
                return new ResponseEntity<>("No records", HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    

    @GetMapping("/profile/{email}")
    public ResponseEntity<Object> getProfile(@PathVariable String email)
    {
        try {
            Optional<Admin> adminData = adminRepository.findByEmail(email);
            if (adminData.isPresent()) {
                return new ResponseEntity<>(adminData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/profile/{email}")
    public ResponseEntity<Object> updateProfile(@PathVariable String email,@RequestBody Admin admin)
    {
        try {
            Optional<Admin> adminData = adminRepository.findByEmail(email);
            if (adminData.isPresent()) {
                Admin _admin = adminData.get();
                    _admin.setName(admin.getName());
                    _admin.setPhone(admin.getPhone());
                    _admin.setAddress(admin.getAddress());
                    return new ResponseEntity<>(adminRepository.save(_admin), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getregistrationexams/{examid}")
    public ResponseEntity<Object> getRegistrationExam(@PathVariable("examid") Long id)
    {
        try{
            return new ResponseEntity<>(registrationRepository.findByExamId(id), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sendmailtoparent/{id}")
    public ResponseEntity<Object> sendMailToParent(@RequestBody Email email,@PathVariable("id") Long id)
    {
        try{
            Optional<Admin> admin=adminRepository.findById(id);
            if(admin.isPresent())
            {
                Admin adminData = admin.get();
                emailSender.sendEmailHTMLAdminToParent(email.getToEmail(), email.getSubject(),email.getBody(), adminData.getPhone(),adminData.getEmail());
            }
            return new ResponseEntity<>("Email Sent to "+email.getToEmail(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
