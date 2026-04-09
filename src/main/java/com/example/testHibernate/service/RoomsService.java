package com.example.testHibernate.service;

import com.example.testHibernate.dto.RoomCreateResponse;
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
    private RoomTypesDAO roomTypesDAO;
    private RoomCreateResponse toResponse(Rooms r){
        return RoomCreateResponse.builder()
                .roomId(r.getRoomId())
                .roomNumber(r.getRoomNumber())

                .typeId(r.getRoomTypes() != null ? r.getRoomTypes().getTypeId() : null)
                .typeName(r.getRoomTypes() != null ? r.getRoomTypes().getTypeName() : null)

                .branchId(r.getBranch() != null ? r.getBranch().getBranchId() : null)
                .branchName(r.getBranch() != null ? r.getBranch().getBranchName() : null)

                .status(r.getStatus())
                .build();
    }
    public List<RoomCreateResponse> getAll(){
        return  roomsDAO.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
    public RoomCreateResponse create(RoomRequest req){
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
        Rooms saved = roomsDAO.save(r);
        return toResponse(saved);
    }
    public RoomCreateResponse update(Integer id,RoomRequest req){
        Rooms r = roomsDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if(req.getRoomNumber() != null){
            r.setRoomNumber(req.getRoomNumber());
        }

        if(req.getTypeId() != null){
            RoomTypes roomType = roomTypesDAO.findById(req.getTypeId())
                    .orElseThrow(() -> new RuntimeException("RoomType not found"));
            r.setRoomTypes(roomType);
        }

        if(req.getBranchId() != null){
            Branches branch = branchesDAO.findById(req.getBranchId())
                    .orElseThrow(() -> new RuntimeException("Branch not found"));
            r.setBranch(branch);
        }

        if(req.getStatus() != null){
            r.setStatus(RoomStatus.fromValue(req.getStatus()));
        }

        Rooms updated = roomsDAO.save(r);

        return toResponse(updated);
    }
    public void delete(Integer id){
        roomsDAO.deleteById(id);
    }
}
