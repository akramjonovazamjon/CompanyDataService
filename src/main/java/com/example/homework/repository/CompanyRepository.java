package com.example.homework.repository;

import com.example.homework.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    boolean existsByCompName(String compName);
}
