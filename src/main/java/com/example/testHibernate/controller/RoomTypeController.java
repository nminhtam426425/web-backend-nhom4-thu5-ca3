package com.example.testHibernate.controller;

import com.example.testHibernate.dto.RoomTypeUpdateRequest;
import com.example.testHibernate.entity.RoomTypes;
import com.example.testHibernate.service.RoomTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000", "https://web-fontend-nhom4-thu5-ca3.vercel.app"})
@RestController
@RequestMapping("/roomtypes")
public class RoomTypeController {
    @Autowired
    private RoomTypesService roomTypesService;
    @GetMapping
    public ResponseEntity<List<RoomTypes>>getAll(){
        List<RoomTypes> roomTypes =  roomTypesService.getAll();
        return ResponseEntity.ok(roomTypes);
    }
    @PostMapping
    public ResponseEntity<RoomTypes> create(@RequestBody RoomTypes rt){
        RoomTypes createdRoomTypes= roomTypesService.create(rt);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoomTypes);
    }
    @PatchMapping("/{id}/baseprice-capacity")
    public ResponseEntity<RoomTypes>updateBasePriceAndCapacity(
            @PathVariable Integer id,
            @RequestBody RoomTypeUpdateRequest req){
        RoomTypes update = roomTypesService.updatePriceAndCapacity(id,req);
        return ResponseEntity.ok(update);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RoomTypes>update(@PathVariable Integer id,@RequestBody RoomTypes rt){
        RoomTypes updateRoomType = roomTypesService.update(id,rt);
        return ResponseEntity.ok(updateRoomType);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        roomTypesService.delete(id);
        return ResponseEntity.ok("Xóa loại phòng với id "+id+" thành công!");
    }

}
