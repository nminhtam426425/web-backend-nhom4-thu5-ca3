package com.example.testHibernate.service;

import com.example.testHibernate.entity.Rooms;
import com.example.testHibernate.enums.RoomStatus;
import com.example.testHibernate.repo.RoomsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsService {
    @Autowired
    private RoomsDAO roomsDAO;

    public List<Rooms> getAll(){
        return  roomsDAO.findAll();
    }
    public Rooms create(Rooms r){
        if(r.getStatus() == null){
            r.setStatus(RoomStatus.EMPTY);
        }
        return roomsDAO.save(r);
    }
    public Rooms update(Integer id,Rooms newRoom){
        Rooms r = roomsDAO.findById(id).orElseThrow(()->new RuntimeException("Room not found"));
        r.setRoomNumber(newRoom.getRoomNumber());
        r.setBranchId(newRoom.getBranchId());
        r.setTypeId(newRoom.getTypeId());
        r.setStatus(newRoom.getStatus());
        return roomsDAO.save(r);
    }
    public void delete(Integer id){
        roomsDAO.deleteById(id);
    }
}
