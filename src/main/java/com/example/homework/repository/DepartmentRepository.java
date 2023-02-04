package com.example.homework.repository;

import com.example.homework.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsByNameAndCompany_Id(String name, Integer company_id);
    boolean existsByNameAndIdNotAndCompany_Id(String name, Integer id, Integer company_id);
    List<Department> findAllByCompany_Id(Integer company_id);
}
