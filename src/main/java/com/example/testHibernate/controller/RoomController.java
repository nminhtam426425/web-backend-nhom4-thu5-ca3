package com.example.testHibernate.controller;

import com.example.testHibernate.dto.RoomAmenityRequest;
import com.example.testHibernate.dto.RoomCreateResponse;
import com.example.testHibernate.dto.RoomRequest;
import com.example.testHibernate.entity.Rooms;
import com.example.testHibernate.response.ApiResponse;
import com.example.testHibernate.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000", "https://web-fontend-nhom4-thu5-ca3.vercel.app"})
@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomsService roomsService;
    @GetMapping
    public ApiResponse<List<RoomCreateResponse>> getAll(){
        return ApiResponse.<List<RoomCreateResponse>>builder()
                .code(200)
                .data(roomsService.getAll())
                .build();
    }
    @PostMapping
    public ApiResponse<RoomCreateResponse> create(@RequestBody RoomRequest r){

        return ApiResponse.<RoomCreateResponse>builder()
                .code(200)
                .data(roomsService.create(r))
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<RoomCreateResponse> update(@PathVariable Integer id,@RequestBody RoomRequest r){

        return ApiResponse.<RoomCreateResponse>builder()
                .code(200)
                .data(roomsService.update(id,r))
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        roomsService.delete(id);
        return ResponseEntity.ok("Xóa phòng có id "+id+" thành công!");
    }
}
