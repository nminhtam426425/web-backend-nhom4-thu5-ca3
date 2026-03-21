package com.example.testHibernate.controller;

import com.example.testHibernate.dto.UserResponse;
import com.example.testHibernate.entity.Users;
import com.example.testHibernate.dto.UserRequest;

import com.example.testHibernate.response.ApiResponse;
import com.example.testHibernate.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://web-fontend-nhom4-thu5-ca3.vercel.app"})
@RestController
public class UserController {
    @Autowired
    private UsersService userService;

    @PostMapping("/users/save")
    public ApiResponse<UserRequest> saveUser(@RequestBody UserRequest users) {
        return ApiResponse.<UserRequest>builder()
                .data(userService.saveUser(users,true))
                .code(1001)
                .build();
    }

    @PutMapping("/users/update")
    public ApiResponse<UserRequest> updateUser(@RequestBody UserRequest users) {

        return ApiResponse.<UserRequest>builder()
                .data(userService.saveUser(users,false))
                .code(1001)
                .build();
    }

    @DeleteMapping("/users/delete")
    public ApiResponse<String> deleteUser(@RequestBody UserRequest users) {
        userService.deleteUser(users);
        return ApiResponse.<String>builder()
                .data(users.getId())
                .code(1001)
                .build();
    }

    @GetMapping("/users")
    public ApiResponse<List<Users>> findAllUsers() {
        return ApiResponse.<List<Users>>builder()
                .data(userService.findAllUsers())
                .code(1001)
                .build();
    }

    @GetMapping("/users/{id}")
    public ApiResponse<UserResponse> findUserById(@PathVariable(name = "id") String id) {
        return ApiResponse.<UserResponse>builder()
                .code(1001)
                .data(userService.getUserById(Integer.parseInt(id)))
                .build();
    }

//    Api khóa tài khoản khách (Bảo)
    @PutMapping("/users/disable/{id}")
    public ApiResponse<String> disableUser(@PathVariable String id){
        userService.disableUser(id);
        return ApiResponse.<String>builder().data("User disabled").code(1001).build();
    }
    //  Api mở tài khoản khách (Bảo)
    @PutMapping("/users/enable/{id}")
    public ApiResponse<String> enableUser(@PathVariable String id){
        userService.enableUser(id);
        return ApiResponse.<String>builder().data("User enabled").code(1001).build();
    }
//    Api lấy tất cả nhân viên (Bảo)
    @GetMapping("/staff")
    public ApiResponse<List<Users>> getAllStaffs(){
        return ApiResponse.<List<Users>>builder().data(userService.findAllStaffs()).code(1001).build();
    }
    @PostMapping("/staff")
    public ApiResponse<String> createStaff(
            @RequestBody UserRequest user,
            @RequestParam Integer branchId){
        userService.createStaff(user,branchId);
        return ApiResponse.<String>builder().data("Create staff success").code(1001).build();
    }
}
