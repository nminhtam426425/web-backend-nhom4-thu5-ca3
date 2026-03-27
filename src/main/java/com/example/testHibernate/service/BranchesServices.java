package com.example.testHibernate.service;

import com.example.testHibernate.dto.BranchResponse;
import com.example.testHibernate.dto.BranchUpdateRequest;
import com.example.testHibernate.entity.Branches;
import com.example.testHibernate.entity.Users;
import com.example.testHibernate.repo.BranchesDAO;
import com.example.testHibernate.repo.RoomsDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchesServices {
    @Autowired
    private BranchesDAO repo;
    @Autowired
    private RoomsDAO roomsDAO;
    public BranchResponse toResponse(Branches branch){
        Long totalRooms = roomsDAO.countByBranchId(branch.getBranchId());
        return BranchResponse.builder()
                .branchId(branch.getBranchId())
                .branchName(branch.getBranchName())
                .address(branch.getAddress())
                .phone(branch.getPhone())
                .email(branch.getEmail())
                .description(branch.getDescription())
                .isActive(branch.getIsActive())
                .rooms(totalRooms)
                .build();
    }
    public List<BranchResponse>getAll(){
        return  repo.findByIsActiveTrue().stream().map(this::toResponse).toList();
    }
    public Branches create(Branches b){
        return repo.save(b);
    }
    public Branches update(Integer id,Branches newB){
        Branches b = repo.findById(id).orElseThrow(()-> new RuntimeException("Not Found"));
        b.setBranchName(newB.getBranchName());
        b.setAddress(newB.getAddress());
        b.setPhone(newB.getPhone());
        b.setEmail(newB.getEmail());
        b.setDescription(newB.getDescription());
        return repo.save(b);
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
    public Branches updateInfo(Integer id, BranchUpdateRequest req){
        Branches b = repo.findById(id).orElseThrow(()-> new RuntimeException("Branch not found"));
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
        return  repo.save(b);
    }
}
