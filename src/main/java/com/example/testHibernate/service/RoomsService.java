package com.example.testHibernate.service;

import com.example.testHibernate.dto.RoomRequest;
import com.example.testHibernate.entity.Amenities;
import com.example.testHibernate.entity.Branches;
import com.example.testHibernate.entity.RoomTypes;
import com.example.testHibernate.entity.Rooms;
import com.example.testHibernate.enums.RoomStatus;
import com.example.testHibernate.repo.AmenitiesDAO;
import com.example.testHibernate.repo.BranchesDAO;
import com.example.testHibernate.repo.RoomTypesDAO;
import com.example.testHibernate.repo.RoomsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsService {
    @Autowired
    private RoomsDAO roomsDAO;
    @Autowired
    private BranchesDAO branchesDAO;
    @Autowired
    private AmenitiesDAO amenitiesDAO;
    @Autowired
    private RoomTypesDAO roomTypesDAO;
    public List<Rooms> getAll(){
        return  roomsDAO.findAll();
    }
    public Rooms create(RoomRequest req){
        Rooms r = new Rooms();
        RoomTypes roomType = roomTypesDAO.findById(req.getTypeId()).orElseThrow(()-> new RuntimeException("RoomType not found"));
        r.setRoomNumber(req.getRoomNumber());
        r.setRoomTypes(roomType);
        if(req.getStatus() == null){
            r.setStatus(RoomStatus.EMPTY);
        }else {
            r.setStatus(RoomStatus.fromValue(req.getStatus()));
        }
        Branches branch = branchesDAO.findById(req.getBranchId())
                .orElseThrow(()->new RuntimeException("Branch not found"));
            r.setBranch(branch);
         if(req.getAmenityIds() != null){
             List<Amenities> amenitites = amenitiesDAO.findAllById(req.getAmenityIds());
             r.setAmenitites(amenitites);
         }
        return roomsDAO.save(r);
    }
    public Rooms update(Integer id,Rooms newRoom){
        Rooms r = roomsDAO.findById(id).orElseThrow(()->new RuntimeException("Room not found"));
        r.setRoomNumber(newRoom.getRoomNumber());
        r.setBranch(newRoom.getBranch());
        r.setRoomTypes(newRoom.getRoomTypes());
        r.setStatus(newRoom.getStatus());
        return roomsDAO.save(r);
    }
    public void delete(Integer id){
        roomsDAO.deleteById(id);
    }
    public Rooms updateAmenities(Integer roomId,List<Integer> amenityIds){
        Rooms room = roomsDAO.findById(roomId).orElseThrow(()->new RuntimeException("Room not found"));
        List<Amenities> amenitites = amenitiesDAO.findAllById(amenityIds);
        room.setAmenitites(amenitites);
        return roomsDAO.save(room);
    }
}
