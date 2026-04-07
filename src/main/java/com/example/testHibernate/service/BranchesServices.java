package com.example.testHibernate.service;

import com.example.testHibernate.dto.BranchRequest;
import com.example.testHibernate.dto.BranchResponse;
import com.example.testHibernate.dto.BranchUpdateRequest;
import com.example.testHibernate.entity.Branches;
import com.example.testHibernate.entity.Users;
import com.example.testHibernate.repo.BranchesDAO;
import com.example.testHibernate.repo.RoomsDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BranchesServices {
    @Autowired
    private BranchesDAO repo;
    @Autowired
    private RoomsDAO roomsDAO;
    public List<BranchResponse>getAll(){
        List<Branches> branches = repo.findByIsActiveTrue();
        List<Object[]> data = roomsDAO.countRoomsByBranch();
        Map<Integer,Long> roomMap = new HashMap<>();
        for (Object[] row :data){
            Integer branchId = (Integer) row[0];
            Long count = (Long) row[1];
            roomMap.put(branchId,count);
        }
        return branches.stream().map(b->{
            Long totalRooms = roomMap.getOrDefault(b.getBranchId(),0L);
            return BranchResponse.builder()
                    .branchId(b.getBranchId())
                    .branchName(b.getBranchName())
                    .address(b.getAddress())
                    .phone(b.getPhone())
                    .email(b.getEmail())
                    .description(b.getDescription())
                    .isActive(b.getIsActive())
                    .rooms(totalRooms)
                    .build();
        }).toList();
    }
    public List<BranchResponse>getAllDisabled(){
        List<Branches> branches = repo.findByIsActiveFalse();
        List<Object[]> data = roomsDAO.countRoomsByBranch();
        Map<Integer,Long> roomMap = new HashMap<>();
        for (Object[] row :data){
            Integer branchId = (Integer) row[0];
            Long count = (Long) row[1];
            roomMap.put(branchId,count);
        }
        return branches.stream().map(b->{
            Long totalRooms = roomMap.getOrDefault(b.getBranchId(),0L);
            return BranchResponse.builder()
                    .branchId(b.getBranchId())
                    .branchName(b.getBranchName())
                    .address(b.getAddress())
                    .phone(b.getPhone())
                    .email(b.getEmail())
                    .description(b.getDescription())
                    .isActive(b.getIsActive())
                    .rooms(totalRooms)
                    .build();
        }).toList();
    }
    public BranchResponse create(BranchRequest req){
        if(req.getBranchName() == null || req.getBranchName().trim().isEmpty()){
            throw new RuntimeException("Tên chi nhánh không được để trống");
        }
        Branches b = new Branches();
        b.setBranchName(req.getBranchName());
        b.setAddress(req.getAddress());
        b.setPhone(req.getPhone());
        b.setEmail(req.getEmail());
        b.setDescription(req.getDescription());
        b.setIsActive(req.getIsActive());
        Branches savedBranches = repo.save(b);
        return BranchResponse.builder()
                .branchId(b.getBranchId())
                .branchName(b.getBranchName())
                .address(b.getAddress())
                .phone(b.getPhone())
                .email(b.getEmail())
                .description(b.getDescription())
                .isActive(b.getIsActive())
                .rooms(0L) // mới tạo chưa có phòng
                .build();
    }
    public BranchResponse update(Integer id,Branches newB){
        Branches b = repo.findById(id).orElseThrow(()-> new RuntimeException("Not Found"));
        if(newB.getBranchName() != null){
            b.setBranchName(newB.getBranchName());
        }
        if(newB.getAddress() != null){
            b.setAddress(newB.getAddress());
        }
       if(newB.getPhone() != null) {
           b.setPhone(newB.getPhone());
       }
       if(newB.getEmail() != null){
           b.setEmail(newB.getEmail());
       }
        if(newB.getDescription() != null){
            b.setDescription(newB.getDescription());
        }
        if(newB.getIsActive() != null){
            b.setIsActive(newB.getIsActive());
        }
        repo.save(b);
        Long totalRooms = roomsDAO.countByBranch_BranchId(b.getBranchId());
        return BranchResponse.builder()
                .branchId(b.getBranchId())
                .branchName(b.getBranchName())
                .address(b.getAddress())
                .phone(b.getPhone())
                .email(b.getEmail())
                .description(b.getDescription())
                .isActive(b.getIsActive())
                .rooms(totalRooms != null ? totalRooms : 0L)
                .build();
    }
    @Transactional
    public void delete(Integer id, Users currentUser){
        if(currentUser.getRoleId() != 1){
            throw new RuntimeException("Không có quyền xóa");
        }
        try {
            repo.deleteById(id);
            repo.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void hide(Integer id){
        Branches b = repo.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
        b.setIsActive(false);
        repo.save(b);
    }
//    Hàm cập nhật thông tin cho chi nhánh
    public BranchResponse updateInfo(Integer id, BranchUpdateRequest req){
        Branches b = repo.findById(id).orElseThrow(()-> new RuntimeException("Branch not found"));
        if(req.getBranchName() != null){
            b.setBranchName(req.getBranchName());
        }
        if(req.getAddress() != null){
            b.setAddress(req.getAddress());
        }
        if(req.getPhone() != null){
            b.setPhone(req.getPhone());
        }
        if(req.getEmail() != null){
            b.setEmail(req.getEmail());
        }
        if(req.getDescription() != null){
            b.setDescription(req.getDescription());
        }
        if(req.getIsActive() != null){
            b.setIsActive(req.getIsActive());
        }
        repo.save(b);
        Long totalRooms = roomsDAO.countByBranch_BranchId(b.getBranchId());
            return BranchResponse.builder()
                    .branchId(b.getBranchId())
                    .branchName(b.getBranchName())
                    .address(b.getAddress())
                    .phone(b.getPhone())
                    .email(b.getEmail())
                    .description(b.getDescription())
                    .isActive(b.getIsActive())
                    .rooms(totalRooms != null ? totalRooms: 0L)
                    .build();
    }
}
