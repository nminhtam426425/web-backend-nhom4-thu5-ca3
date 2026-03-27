package com.example.testHibernate.controller;

import com.example.testHibernate.entity.RoomImages;
import com.example.testHibernate.service.RoomImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000", "https://web-fontend-nhom4-thu5-ca3.vercel.app"})
@RestController
@RequestMapping("/room-images")
public class RoomImagesController {
    @Autowired
   private RoomImagesService roomImagesService;
    @PostMapping
    public ResponseEntity<RoomImages>upload(
            @RequestParam Integer typeId,
            @RequestParam String imageUrl
    ){
        RoomImages uploadedImage = roomImagesService.upload(typeId,imageUrl);
        return ResponseEntity.ok(uploadedImage);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RoomImages>update(@PathVariable Integer id,
                                            @RequestParam String imageUrl){
        RoomImages updatedImage = roomImagesService.update(id,imageUrl);
        return ResponseEntity.ok(updatedImage);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String>delete(@PathVariable Integer id){
        roomImagesService.delete(id);
        return ResponseEntity.ok("Đã xóa ảnh có id "+id);
    }

}
