package com.example.testHibernate.service;

import com.example.testHibernate.entity.RoomImages;
import com.example.testHibernate.entity.RoomTypes;
import com.example.testHibernate.repo.RoomImagesDAO;
import com.example.testHibernate.repo.RoomTypesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomImagesService {
    @Autowired
    private RoomImagesDAO roomImagesDAO;
    @Autowired
    private RoomTypesDAO roomTypesDAO;
    public RoomImages upload(Integer typeId,String imageUrl){
        RoomTypes type = roomTypesDAO.findById(typeId).orElseThrow(()->new RuntimeException("Room type not found"));
        RoomImages img = new RoomImages();
        img.setImageUrl(imageUrl);
        img.setRoomType(type);
        return roomImagesDAO.save(img);
    }
    public RoomImages update(Integer id,String imageUrl){
        RoomImages img = roomImagesDAO.findById(id)
                .orElseThrow(()->new RuntimeException("Image not found"));
        img.setImageUrl(imageUrl);
        return roomImagesDAO.save(img);
    }
    public void delete(Integer id){
        roomImagesDAO.deleteById(id);
    }
}
