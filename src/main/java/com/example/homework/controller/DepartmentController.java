package com.example.homework.controller;

import com.example.homework.entity.Department;
import com.example.homework.payload.ApiResponse;
import com.example.homework.payload.DepartmentDto;
import com.example.homework.payload.DepartmentUpdateDto;
import com.example.homework.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/company/{id}")
    public HttpEntity<List<Department>> getCompanyDepartments(@PathVariable Integer id) {
        List<Department> companyDepartments = departmentService.getCompanyDepartment(id);
        return ResponseEntity.ok(companyDepartments);
    }

    @GetMapping("/{id}")
    public HttpEntity<Department> getDepartmentById(@PathVariable Integer id) {
        Department departmentById = departmentService.getDepartmentById(id);
        return ResponseEntity.status(Objects.isNull(departmentById) ? 409 : 200).body(departmentById);
    }

    @PostMapping
    public HttpEntity<ApiResponse> saveDepartment(@RequestBody DepartmentDto departmentDto) {
        ApiResponse apiResponse = departmentService.saveDepartment(departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> updateDepartment(@PathVariable Integer id, @RequestBody DepartmentUpdateDto departmentUpdateDto) {
        ApiResponse apiResponse = departmentService.updateDepartment(id, departmentUpdateDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteDepartment(@PathVariable Integer id) {
        ApiResponse apiResponse = departmentService.deleteDepartment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
