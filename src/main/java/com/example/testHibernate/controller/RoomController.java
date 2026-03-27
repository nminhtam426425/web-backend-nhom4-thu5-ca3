package com.example.testHibernate.controller;

import com.example.testHibernate.dto.RoomAmenityRequest;
import com.example.testHibernate.dto.RoomRequest;
import com.example.testHibernate.entity.Rooms;
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
    public ResponseEntity<List<Rooms>> getAll(){
        List<Rooms> rooms = roomsService.getAll();
        return ResponseEntity.ok(rooms);
    }
    @PostMapping
    public ResponseEntity<Rooms> create(@RequestBody RoomRequest r){
        Rooms createdRoom = roomsService.create(r);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Rooms> update(@PathVariable Integer id,@RequestBody Rooms r){
        Rooms updatedRoom = roomsService.update(id,r);
        return ResponseEntity.ok(updatedRoom);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        roomsService.delete(id);
        return ResponseEntity.ok("Xóa phòng có id "+id+" thành công!");
    }
    @PutMapping("/{id}/amenities")
    public ResponseEntity<Rooms> updateAmenities(@PathVariable Integer id,
                                                 @RequestBody RoomAmenityRequest req){
        Rooms updateAmenities = roomsService.updateAmenities(id,req.getAmenityIds());
        return ResponseEntity.ok(updateAmenities);
    }
}
