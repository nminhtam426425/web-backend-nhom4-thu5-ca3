package com.example.testHibernate.controller;

import com.example.testHibernate.dto.UserResponse;
import com.example.testHibernate.entity.Users;
import com.example.testHibernate.response.ApiResponse;
import com.example.testHibernate.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000", "https://web-fontend-nhom4-thu5-ca3.vercel.app"})
@RestController
public class StaffController {
    @Autowired
    UsersService usersService;

        @PutMapping("/staff/disable/{id}")
        public ApiResponse<String> disableStaff(@PathVariable String id){
        usersService.disableStaff(id);
        return ApiResponse.<String>builder().data("Staff disabled").code(1001).build();
    }
    @PutMapping("/staff/enable/{id}")
    public ApiResponse<String> enableStaff(@PathVariable String id){
        usersService.enableStaff(id);
        return ApiResponse.<String>builder().data("Staff enabled").code(1001).build();
    }
//    Truyền id chi nhánh trả về danh sách các staff  tương ứng.
    @GetMapping("/staff/branch/{branchId}")
    public ApiResponse<List<UserResponse>>getStaffByBranch(@PathVariable Integer branchId){
            return ApiResponse.<List<UserResponse>>builder().data(usersService.getStaffByBranch(branchId)).code(1001).build();
    }
    @GetMapping("/staff/disabled")
    public ApiResponse<List<Users>> getDisabledStaffs(){
        return ApiResponse.<List<Users>>builder()
                .data(usersService.getDisabledStaffs())
                .code(1001)
                .build();
    }
}
