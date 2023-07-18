package com.bezkoder.spring.jpa.h2.repository;

import com.bezkoder.spring.jpa.h2.model.Registration;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByExamId(Long id);

    void deleteByExamId(Long examId);

    List<Registration> findByExamIdAndVolunteerId(Long examId, Long volunteerId);

    List<Registration> findByVolunteerId(Long id);
    
}
