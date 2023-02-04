package com.example.homework.service;

import com.example.homework.entity.Address;
import com.example.homework.entity.Company;
import com.example.homework.payload.ApiResponse;
import com.example.homework.payload.CompanyDto;
import com.example.homework.repository.AddressRepository;
import com.example.homework.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;

    public ApiResponse saveCompany(CompanyDto companyDto) {
        if (companyRepository.existsByCompName(companyDto.getCompName())) {
            return new ApiResponse("Company already exist", false);
        }
        Address savedAddress = addressRepository.save(
                Address.builder()
                        .homeNumber(companyDto.getHomeNumber())
                        .street(companyDto.getStreet())
                        .build()
        );
        companyRepository.save(
                Company.builder()
                        .compName(companyDto.getCompName())
                        .directorName(companyDto.getDirectorName())
                        .address(savedAddress)
                        .build()
        );
        return new ApiResponse("Company saved", true);
    }

    public Company getCompanyById(Integer id) {
        return companyRepository.findById(id).orElse(null);
    }

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public ApiResponse deleteCompany(Integer id) {
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Company deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Company not found", false);
        }
    }

    public ApiResponse updateCompany(Integer id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) {
            return new ApiResponse("Company not found", false);
        }
        Company company = optionalCompany.get();
        Address address = company.getAddress();
        address.setHomeNumber(companyDto.getHomeNumber());
        address.setStreet(companyDto.getStreet());
        addressRepository.save(address);
        company.setCompName(companyDto.getCompName());
        company.setDirectorName(companyDto.getDirectorName());
        companyRepository.save(company);
        return new ApiResponse("Company updated", true);
    }
}
