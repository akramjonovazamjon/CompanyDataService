package com.example.homework.service;

import com.example.homework.entity.Address;
import com.example.homework.entity.Department;
import com.example.homework.entity.Worker;
import com.example.homework.payload.ApiResponse;
import com.example.homework.payload.WorkerDto;
import com.example.homework.repository.AddressRepository;
import com.example.homework.repository.DepartmentRepository;
import com.example.homework.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final AddressRepository addressRepository;
    private final DepartmentRepository departmentRepository;

    public ApiResponse saveWorker(WorkerDto workerDto){
        if (workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber())){
            return new ApiResponse("Worker already exist", false);
        }
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (optionalDepartment.isEmpty()){
            return new ApiResponse("Department not found", false);
        }
        Address savedAddress = addressRepository.save(
                Address.builder()
                        .street(workerDto.getStreet())
                        .homeNumber(workerDto.getHomeNumber())
                        .build()
        );
        Worker worker = Worker.builder()
                .name(workerDto.getName())
                .phoneNumber(workerDto.getPhoneNumber())
                .address(savedAddress)
                .department(optionalDepartment.get())
                .build();
        workerRepository.save(worker);
        return new ApiResponse("Worker saved", true);
    }

    public ApiResponse updateWorker(Integer id, WorkerDto workerDto){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isEmpty()){
            return new ApiResponse("Worker not found", false);
        }
        if (workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id)){
            return new ApiResponse("Worker already exist", false);
        }
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (optionalDepartment.isEmpty()){
            return new ApiResponse("Department not found", false);
        }
        Worker worker = optionalWorker.get();
        Address address = worker.getAddress();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        addressRepository.save(address);
        worker.setDepartment(optionalDepartment.get());
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        workerRepository.save(worker);
        return new ApiResponse("Worker updated", true);
    }

    public List<Worker> getCompanyWorkers(Integer companyId){
        return workerRepository.findAllByDepartment_Company_Id(companyId);
    }

    public List<Worker> getDepartmentWorkers(Integer departmentId){
        return workerRepository.findAllByDepartment_Id(departmentId);
    }

    public Worker getWorkerById(Integer id){
        return workerRepository.findById(id).orElse(null);
    }

    public ApiResponse deleteWorkerById(Integer id){
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Worker deleted", true);
        }catch (Exception e){
            return new ApiResponse("Worker not found", false);
        }
    }
}
