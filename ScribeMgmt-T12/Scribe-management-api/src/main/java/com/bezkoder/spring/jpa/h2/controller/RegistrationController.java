package com.bezkoder.spring.jpa.h2.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import com.bezkoder.spring.jpa.h2.service.model.ListofStudent;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@CrossOrigin
@RestController
@RequestMapping("/registration")
@Transactional
public class RegistrationController {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ExamRepository examRepository;
    @Autowired
    RegistrationRepository registrationRepository;
    @Autowired
    VolunteerRepository volunteerRepository;
    @Autowired
    EmailSender emailSender;

    // @PostMapping("/examstudent/{studentid}/{examid}")
    // public ResponseEntity<Object> linkExamAndStudent(@PathVariable("studentid") Long studentId, @PathVariable("examid") Long examId)
    // {
    //     try{
    //         Student student = studentRepository.findById(studentId)
    //             .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
    //         Exam exam = examRepository.findById(examId)
    //                 .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));
    //         Registration registration = new Registration();
    //         registration.setStudent(student);
    //         registration.setExam(exam);
    //         registration.setVolunteer(null);

    //         registrationRepository.save(registration);
    //         return new ResponseEntity<>("Linked Student and Exam", HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    //     catch (Exception e) {
    //         return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @PostMapping("/examstudents/{examid}")
    public ResponseEntity<Object> linkExamAndStudents(@RequestBody ListofStudent students, @PathVariable("examid") Long examId)
    {
        try{
            registrationRepository.deleteByExamId(examId);
            for(int i=0;i<students.getArr().size();i++)
            {
                    Student student = studentRepository.findById(students.getArr().get(i))
                .orElseThrow(() -> new RuntimeException("Student id not able to find"));
                    Exam exam = examRepository.findById(examId)
                            .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));
                    Registration registration = new Registration();
                    registration.setStudent(student);
                    registration.setExam(exam);
                    registration.setVolunteer(null);

                    registrationRepository.save(registration);
            }
            return new ResponseEntity<>("Linked Student and Exam", HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Registration getRegistrationWithoutVolunteer(Long examId){
        Iterable<Registration> registrations = registrationRepository.findAll();
        for (Registration registration : registrations) {
            if(registration.getVolunteer() == null && registration.getExam().getId() == examId){
                return registration;
            }
        }
        return null;
    }

    @GetMapping("/accept/{volunteerid}/{examid}")
    public ResponseEntity<Object> linkExamAndVolunteer(@PathVariable("volunteerid") Long volunteerId, @PathVariable("examid") Long examId)
    {
        try{
            Optional<Volunteer> volunteerData = volunteerRepository.findById(volunteerId);
            if (volunteerData.isPresent()) {
                Volunteer volunteer = volunteerData.get();
                Exam exam = examRepository.findById(examId)
                        .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));
                List<Registration> registrations = registrationRepository.findByExamIdAndVolunteerId(examId, volunteerId);
                if(registrations.size()>=1)
                {
                    return new ResponseEntity<>("You have already registred for this exam", HttpStatus.OK);
                }
                Registration registration = getRegistrationWithoutVolunteer(examId);
                if(registration==null)
                {
                    return new ResponseEntity<>("Already we got volunteers thank you for your interest", HttpStatus.CREATED);
                }
                registration.setVolunteer(volunteer);
                registrationRepository.save(registration);
                return new ResponseEntity<>("Thank You for Volunteering", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Volunteer not found", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/invite/{examid}")
    public ResponseEntity<Object> inviteVolunteer(@PathVariable("examid") Long examId)
    {
        try{
            Exam exam = examRepository.findById(examId)
                    .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));
            Iterable<Volunteer> volunteers = volunteerRepository.findAll();
            List<Volunteer> invitedVolunteers = new ArrayList<>();
            Iterable<Registration> registrations = registrationRepository.findAll();
            for (Volunteer volunteer : volunteers) {
                Boolean invited = true;
                for (Registration registration : registrations){
                    if(registration.getVolunteer()!=null && registration.getVolunteer().getId() == volunteer.getId()){
                        if(registration.getExam().getId() == exam.getId()){
                            invited = false;
                        }
                        Date date1 = exam.getDate();
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.setTime(date1);
                        int year1 = calendar1.get(Calendar.YEAR);
                        int month1 = calendar1.get(Calendar.MONTH);
                        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
                        Date date2 =registration.getExam().getDate();
                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.setTime(date2);
                        int year2 = calendar2.get(Calendar.YEAR);
                        int month2 = calendar2.get(Calendar.MONTH);
                        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
                        if(year1==year2&&month1==month2&&day1==day2){
                            invited = false;
                        }
                    }
                }
                if(Boolean.TRUE.equals(invited)==true){
                    invitedVolunteers.add(volunteer);
                }
            }
            List<CompletableFuture<Void>> futures=new ArrayList<>();
            for (Volunteer volunteer : invitedVolunteers) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
                    try {
                        emailSender.sendEmailHTMLToVoluntter(volunteer.getEmail(), "Invitation to Volunteer", exam.getDate(), "http://localhost:4200/user/volunteer/checkin?examId="+examId+"&volId="+volunteer.getId());
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    // emailSender.sendEmail(volunteer.getEmail(), "Invitation to Volunteer", "You have been invited to volunteer for an exam on " + exam.getDate() + ". Please login to the website to accept or decline the invitation."+
                    // "<a href=\"http://localhost:4200/user/volunteer/checkin?examId="+examId+"&volId="+volunteer.getId()+"\"></a>");
                });
                futures.add(future);
            }            
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            return new ResponseEntity<>("Invited Eligible Volunteers", HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Scheduled(cron = "0 10 0 * * *")
    public void sendRemainder(){
        Date date1 = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        int year1 = calendar1.get(Calendar.YEAR);
        int month1 = calendar1.get(Calendar.MONTH);
        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
        try{
            Iterable<Registration> registrations = registrationRepository.findAll();
            for(Registration registration : registrations){
                Date date2=registration.getExam().getDate();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(date2);
                int year2 = calendar2.get(Calendar.YEAR);
                int month2 = calendar2.get(Calendar.MONTH);
                int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
                if(year1==year2&&month1==month2&&day1==day2 && (registration.getVolunteer()!=null))
                {
                    emailSender.sendEmailHTMLAutomatically(registration.getVolunteer().getEmail(), "Remainder for Exam", registration.getExam().getDate());   
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
    }

}
