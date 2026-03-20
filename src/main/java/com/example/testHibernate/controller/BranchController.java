package com.example.testHibernate.controller;

import com.example.testHibernate.dto.BranchUpdateRequest;
import com.example.testHibernate.entity.Branches;
import com.example.testHibernate.entity.Users;
import com.example.testHibernate.repo.UsersDAO;
import com.example.testHibernate.response.ApiResponse;
import com.example.testHibernate.service.BranchesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {
    @Autowired
    private BranchesServices services;
    @Autowired
    private UsersDAO userDao;

    @GetMapping
    public List<Branches> getAll(){
        return services.getAll();
    }

    @PostMapping
    public Branches create(@RequestBody Branches b,@RequestParam String userId)
    {
        Users user = userDao.findById(userId).orElseThrow();
        if(user.getRoleId() != 1){
            throw new RuntimeException("Chỉ admin mới cho tạo");
        }
        return services.create(b);
    }

    @PutMapping("/{id}")
    public Branches update(@PathVariable Integer id,@RequestBody Branches b){
        return  services.update(id,b);
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id,@RequestParam String userId){
        Users user = userDao.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        services.delete(id,user);
        return "Deleted";
    }

    @PutMapping("/hide/{id}")
    public String hide(@PathVariable Integer id){
        services.hide(id);
        return "Hidden";
    }
    @PutMapping("/update-info/{id}")
    public ApiResponse<Branches> updateInfo(
            @PathVariable Integer id,
            @RequestBody BranchUpdateRequest req
            ) {
        Branches updatedB = services.updateInfo(id,req);
        return new ApiResponse<>(updatedB,200,"Update success");
    }
}
