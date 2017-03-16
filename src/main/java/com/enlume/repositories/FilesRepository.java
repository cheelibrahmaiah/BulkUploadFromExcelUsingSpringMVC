package com.enlume.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enlume.entities.Uploaded_Files;

@Repository
public interface FilesRepository extends JpaRepository<Uploaded_Files, Integer> {

}
