package com.example.homework.controller;

import com.example.homework.entity.Company;
import com.example.homework.payload.ApiResponse;
import com.example.homework.payload.CompanyDto;
import com.example.homework.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public HttpEntity<ApiResponse> addCompany(@RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.saveCompany(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<List<Company>> getCompanies() {
        List<Company> companies = companyService.getCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public HttpEntity<Company> getCompanyById(@PathVariable Integer id) {
        Company companyById = companyService.getCompanyById(id);
        return ResponseEntity.status(Objects.isNull(companyById) ? 409 : 200).body(companyById);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> updateCompany(@PathVariable Integer id, @RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.updateCompany(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteCompany(@PathVariable Integer id) {
        ApiResponse apiResponse = companyService.deleteCompany(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
