package com.result.view.repository;

import com.result.view.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, String> {

    // Correct method without static
    Optional<Student> findByRollNumberAndDateOfBirth(String rollNumber, LocalDate dateOfBirth);

    // Existing method to find by Roll Number
    Optional<Student> findByRollNumber(String rollNumber);
}
