package com.example.testHibernate.controller;

import com.example.testHibernate.entity.Users;
import com.example.testHibernate.dto.UserRequest;
import com.example.testHibernate.response.ApiResponse;
import com.example.testHibernate.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://web-fontend-nhom4-thu5-ca3.vercel.app")
@RestController
public class UserController {
    @Autowired
    private UsersService userService;

    @PostMapping("users/save")
    public ApiResponse<String> saveUser(@RequestBody UserRequest users) {
        userService.saveUser(users,true);
        return ApiResponse.<String>builder()
                .data(users.toString())
                .code(1001)
                .build();
    }

    @PutMapping("users/update")
    public ApiResponse<String> updateUser(@RequestBody UserRequest users) {
        userService.saveUser(users,false);
        return ApiResponse.<String>builder()
                .data(users.toString())
                .code(1001)
                .build();
    }

    @DeleteMapping("/users/delete")
    public ApiResponse<String> deleteUser(@RequestBody UserRequest users) {
        userService.deleteUser(users);
        return ApiResponse.<String>builder()
                .data(users.toString())
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
}
