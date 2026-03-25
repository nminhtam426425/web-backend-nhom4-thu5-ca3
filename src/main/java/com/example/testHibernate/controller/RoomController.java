package com.example.testHibernate.controller;

import com.example.testHibernate.entity.Rooms;
import com.example.testHibernate.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomsService roomsService;
    @GetMapping
    public List<Rooms> getAll(){
        return roomsService.getAll();
    }
    @PostMapping
    public Rooms create(@RequestBody Rooms r){
        return roomsService.create(r);
    }
    @PutMapping("/{id}")
    public Rooms update(@PathVariable Integer id,@RequestBody Rooms r){
        return roomsService.update(id,r);
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        roomsService.delete(id);
        return "Deleted";
    }
}
