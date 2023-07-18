package com.student.repo;

import com.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,Long> {
    void deleteStudentById(Long id);
    Optional<Student> findStudentById(Long id);
    Optional<Student>findStudentByEmailAndPassword(String email,String password);
}
