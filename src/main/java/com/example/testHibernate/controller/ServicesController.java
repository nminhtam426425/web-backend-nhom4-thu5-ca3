package com.example.testHibernate.controller;

import com.example.testHibernate.dto.ServiceRequest;
import com.example.testHibernate.dto.ServiceResponse;
import com.example.testHibernate.repo.BookingServicesDAO;
import com.example.testHibernate.response.ApiResponse;
import com.example.testHibernate.service.ServiceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://web-fontend-nhom4-thu5-ca3.vercel.app"})
@RestController
@RequestMapping("/services")
public class ServicesController {
    @Autowired
    private ServiceServices serviceServices;

    @Autowired
    private BookingServicesDAO bookingServicesDAO;

    @PostMapping
    public ApiResponse<ServiceResponse>  create(@RequestBody ServiceRequest req){
        return ApiResponse.<ServiceResponse>builder()
                .code(200)
                .data(serviceServices.create(req))
                .build();
    }
    @GetMapping
    public ApiResponse<List<ServiceResponse>> getAll(){
        return ApiResponse.<List<ServiceResponse>>builder()
                .code(200)
                .data(serviceServices.getAll())
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<ServiceResponse>  update(
            @PathVariable String id,
            @RequestBody ServiceRequest req){
        return ApiResponse.<ServiceResponse>builder().code(200).data(serviceServices.update(id,req)).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>  delete(@PathVariable String id){
        serviceServices.delete(id, bookingServicesDAO);
        return ResponseEntity.ok("Đã xóa dịch vụ có mã "+id+" thành công");
    }
}
