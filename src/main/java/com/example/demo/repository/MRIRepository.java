package com.example.demo.repository;

import com.example.demo.Entities.MRI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MRIRepository extends JpaRepository<MRI, Long> {

}
