package com.example.homework.service;

import com.example.homework.entity.Company;
import com.example.homework.entity.Department;
import com.example.homework.payload.ApiResponse;
import com.example.homework.payload.DepartmentDto;
import com.example.homework.payload.DepartmentUpdateDto;
import com.example.homework.repository.AddressRepository;
import com.example.homework.repository.CompanyRepository;
import com.example.homework.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    public ApiResponse saveDepartment(DepartmentDto departmentDto) {
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isEmpty()) {
            return new ApiResponse("Company not found", false);
        }
        if (departmentRepository.existsByNameAndCompany_Id(departmentDto.getName(), optionalCompany.get().getId())) {
            return new ApiResponse("Department already exist", false);
        }
        Department department = Department.builder()
                .name(departmentDto.getName())
                .company(optionalCompany.get())
                .build();
        departmentRepository.save(department);
        return new ApiResponse("Department saved", true);
    }

    public ApiResponse updateDepartment(Integer id, DepartmentUpdateDto departmentUpdateDto) {

        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty()) {
            return new ApiResponse("Department not found", false);
        }
        Department department = optionalDepartment.get();
        boolean exists = departmentRepository.existsByNameAndIdNotAndCompany_Id(
                departmentUpdateDto.getName(), id, department.getCompany().getId()
        );
        if (exists) {
            return new ApiResponse("Department exist", false);
        }
        department.setName(departmentUpdateDto.getName());
        departmentRepository.save(department);
        return new ApiResponse("Department updated", true);
    }

    public ApiResponse deleteDepartment(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Department deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Department not found", false);
        }
    }

    public List<Department> getCompanyDepartment(Integer companyId) {
        return departmentRepository.findAllByCompany_Id(companyId);
    }

    public Department getDepartmentById(Integer id) {
        return departmentRepository.findById(id).orElse(null);
    }
}
