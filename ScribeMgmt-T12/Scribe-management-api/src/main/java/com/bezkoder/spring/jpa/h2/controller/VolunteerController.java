package com.bezkoder.spring.jpa.h2.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.h2.model.Tutorial;
import com.bezkoder.spring.jpa.h2.model.Volunteer;
import com.bezkoder.spring.jpa.h2.repository.RegistrationRepository;
import com.bezkoder.spring.jpa.h2.repository.VolunteerRepository;
import com.bezkoder.spring.jpa.h2.service.EmailSender;
import com.bezkoder.spring.jpa.h2.service.HashPassword;
import com.bezkoder.spring.jpa.h2.service.model.Email;
import com.bezkoder.spring.jpa.h2.service.model.Login;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/volunteer")
public class VolunteerController {
    
    @Autowired
    VolunteerRepository volunteerRepository;
    @Autowired
    RegistrationRepository registrationRepository;
    @Autowired
    EmailSender emailSender;

    @PostMapping("/register")
    public ResponseEntity<Object> createVolunteer(@Valid @RequestBody Volunteer volunteer, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        try{
            Optional<Volunteer> volunteerData = volunteerRepository.findByEmail(volunteer.getEmail());
            if (volunteerData.isPresent()) {
                return new ResponseEntity<>("Email is already registered", HttpStatus.CONFLICT);
            }
            String hash=HashPassword.hashedPassword(volunteer.getPassword());
            Volunteer _volunteer = volunteerRepository.save(new Volunteer(volunteer.getName(),volunteer.getPhone(),volunteer.getEmail(),volunteer.getAddress(),hash));
            return new ResponseEntity<>(_volunteer, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginVolunteer(@RequestBody Login login)
    {
        try {
            Optional<Volunteer> volunteerData = volunteerRepository.findByEmail(login.getEmail());
            if (volunteerData.isPresent()) {
                Volunteer volunteer = volunteerData.get();
                boolean flag = HashPassword.checkPassword(login.getPassword(),volunteer.getPassword());
                if (flag) {
                    return new ResponseEntity<>(volunteer, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>("Volunteer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<Object> getProfile(@PathVariable String email)
    {
        try {
            Optional<Volunteer> volunteerData = volunteerRepository.findByEmail(email);
            if (volunteerData.isPresent()) {
                return new ResponseEntity<>(volunteerData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Volunteer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/profile/{email}")
    public ResponseEntity<Object> updateProfile(@PathVariable String email,@RequestBody Volunteer volunteer)
    {
        try {
            Optional<Volunteer> volunteerData = volunteerRepository.findByEmail(email);
            if (volunteerData.isPresent()) {
                Volunteer _volunteer = volunteerData.get();
                    _volunteer.setName(volunteer.getName());
                    _volunteer.setAddress(volunteer.getAddress());
                    _volunteer.setPhone(volunteer.getPhone());
                    return new ResponseEntity<>(volunteerRepository.save(_volunteer), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Volunteer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/volunteeredexams/{id}")
    public ResponseEntity<Object> getVolunteeredExams(@PathVariable("id") Long id)
    {
        try{
            return new ResponseEntity<>(registrationRepository.findByVolunteerId(id), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sendmailtoparent/{id}")
    public ResponseEntity<Object> sendMailToParent(@RequestBody Email email,@PathVariable("id") Long id)
    {
        try{
            Optional<Volunteer> volunteer=volunteerRepository.findById(id);
            if(volunteer.isPresent())
            {
                Volunteer volunteerData = volunteer.get();
                emailSender.sendEmailHTMLVolunteerToParent(email.getToEmail(), email.getSubject(),email.getBody(), volunteerData.getPhone(),volunteerData.getEmail(), volunteerData.getName());
                return new ResponseEntity<>("Email Sent to "+email.getToEmail(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Volunteer Not Found", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping("/notregistred/{id}")
    // public ResponseEntity<Object> notRegistred(@PathVariable("id") Long id)
    // {
    //     try{
            
    //         return new ResponseEntity<>(null, HttpStatus.OK);
    //     }
    //     catch (Exception e){
    //         return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
}
