package com.enlume.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enlume.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	List<Student> findByStatus(byte b);
}
