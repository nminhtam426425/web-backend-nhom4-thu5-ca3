package com.example.testHibernate.controller;

import com.example.testHibernate.dto.BranchResponse;
import com.example.testHibernate.dto.BranchUpdateRequest;
import com.example.testHibernate.entity.Branches;
import com.example.testHibernate.entity.Users;
import com.example.testHibernate.repo.UsersDAO;
import com.example.testHibernate.response.ApiResponse;
import com.example.testHibernate.service.BranchesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000", "https://web-fontend-nhom4-thu5-ca3.vercel.app"})
@RestController
@RequestMapping("/branches")
public class BranchController {
    @Autowired
    private BranchesServices services;
    @Autowired
    private UsersDAO userDao;

    @GetMapping
    public ResponseEntity<List<BranchResponse>> getAll(){
        List<BranchResponse> branches = services.getAll();
        return ResponseEntity.ok(branches);
    }
    @GetMapping("/disabled")
    public ResponseEntity<List<BranchResponse>> getAllDisabled(){
        List<BranchResponse> branches = services.getAllDisabled();
        return ResponseEntity.ok(branches);
    }
    @PostMapping
    public ResponseEntity<Branches> create(@RequestBody Branches b,@RequestParam String userId)
    {
        Users user = userDao.findById(userId).orElseThrow();
        if(user.getRoleId() != 1){
            throw new RuntimeException("Chỉ admin mới cho tạo");
        }
        Branches created = services.create(b);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branches> update(@PathVariable Integer id,@RequestBody Branches b){
        Branches updatedBranches = services.update(id,b);
        return ResponseEntity.ok(updatedBranches);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id,@RequestParam String userId){
        Users user = userDao.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        try {
            services.delete(id,user);
            return ResponseEntity.ok("Đã xóa chi nhánh có id "+id+" thành công");
        }catch (DataIntegrityViolationException e){
            return ResponseEntity.badRequest().body(
                    "Không thể xóa vì dữ liệu ràng buộc ở bảng khác"
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/hide/{id}")
    public ResponseEntity<ApiResponse<String>> hide(@PathVariable Integer id,@RequestParam String userId){
        Users user = userDao.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        if(user.getRoleId() != 1){
            throw new RuntimeException("User này không có quyền ẩn chi nhánh");
        }
        services.hide(id);
        return ResponseEntity.ok(new ApiResponse<>(null,200,"Đã ẩn chi nhánh có id "+id));
    }
    @PutMapping("/update-info/{id}")
    public ApiResponse<Branches> updateInfo(
            @PathVariable Integer id,
            @RequestBody BranchUpdateRequest req
            ) {
        Branches updatedB = services.updateInfo(id,req);
        return new ApiResponse<>(updatedB,200,"Cập nhật thành công chi nhánh có id "+id);
    }
}
