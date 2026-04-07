package com.example.testHibernate.controller;

import com.example.testHibernate.dto.AmenityResponse;
import com.example.testHibernate.entity.Amenities;
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
    public ResponseEntity<List<AmenityResponse>>getAll(){
        List<AmenityResponse> amenities = amenitiesService.getAll();
        return ResponseEntity.ok(amenities);
    }
    @PostMapping
    public ResponseEntity<AmenityResponse>create(@RequestBody Amenities a){
        AmenityResponse createdAmenity = amenitiesService.create(a);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAmenity);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AmenityResponse>update(@PathVariable Integer id,@RequestBody Amenities a){
        AmenityResponse updatedAmenity = amenitiesService.update(id,a);
        return ResponseEntity.ok(updatedAmenity);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>delete(@PathVariable Integer id){
        amenitiesService.delete(id);
        return ResponseEntity.ok("Đã xóa nội thất tiện nghi có id "+id+" thành công");
    }
}
