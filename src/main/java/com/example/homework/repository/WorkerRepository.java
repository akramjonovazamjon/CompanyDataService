package com.example.homework.repository;

import com.example.homework.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
    List<Worker> findAllByDepartment_Company_Id(Integer department_company_id);
    List<Worker> findAllByDepartment_Id(Integer department_id);
}
