package com.example.homework.controller;

import com.example.homework.entity.Worker;
import com.example.homework.payload.ApiResponse;
import com.example.homework.payload.WorkerDto;
import com.example.homework.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/worker")
public class WorkerController {
    private final WorkerService workerService;

    @GetMapping("/company/{id}")
    public HttpEntity<List<Worker>> getCompanyWorkers(@PathVariable Integer id) {
        List<Worker> companyWorkers = workerService.getCompanyWorkers(id);
        return ResponseEntity.ok(companyWorkers);
    }

    @GetMapping("/department/{id}")
    public HttpEntity<List<Worker>> getDepartmentWorkers(@PathVariable Integer id) {
        List<Worker> departmentWorkers = workerService.getDepartmentWorkers(id);
        return ResponseEntity.ok(departmentWorkers);
    }

    @GetMapping("/{id}")
    public HttpEntity<Worker> getWorkerById(@PathVariable Integer id) {
        Worker workerById = workerService.getWorkerById(id);
        return ResponseEntity.ok(workerById);
    }

    @PostMapping
    public HttpEntity<ApiResponse> saveWorker(@RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.saveWorker(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> updateWorker(@PathVariable Integer id, @RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.updateWorker(id, workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteWorker(@PathVariable Integer id) {
        ApiResponse apiResponse = workerService.deleteWorkerById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
