package com.example.testHibernate.controller;

import com.example.testHibernate.dto.AmenityResponse;
import com.example.testHibernate.entity.Amenities;
import com.example.testHibernate.response.ApiResponse;
import com.example.testHibernate.service.AmenitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://web-fontend-nhom4-thu5-ca3.vercel.app"})
@RestController
@RequestMapping("/amenities")
public class AmenityController {
    @Autowired
    private AmenitiesService amenitiesService;
    @GetMapping
    public ApiResponse<List<AmenityResponse>> getAll(){
        return ApiResponse.<List<AmenityResponse>>builder()
                .code(200)
                .data(amenitiesService.getAll())
                .build();
    }
    @PostMapping
    public ApiResponse<AmenityResponse>create(@RequestBody Amenities a){
       return ApiResponse.<AmenityResponse>builder()
               .code(200)
               .data(amenitiesService.create(a))
               .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<AmenityResponse>update(@PathVariable Integer id,@RequestBody Amenities a){
        return ApiResponse.<AmenityResponse>
                builder().
                code(200)
                .data(amenitiesService.update(id,a))
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>delete(@PathVariable Integer id){
        amenitiesService.delete(id);
        return ResponseEntity.ok("Đã xóa nội thất tiện nghi có id "+id+" thành công");
    }
}
