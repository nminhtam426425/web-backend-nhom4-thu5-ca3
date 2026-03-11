package com.example.testHibernate.controller;

import com.example.testHibernate.dto.UserResponse;
import com.example.testHibernate.entity.Users;
import com.example.testHibernate.dto.UserRequest;
import com.example.testHibernate.response.ApiResponse;
import com.example.testHibernate.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
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

    @GetMapping("/users/{id}")
    public ApiResponse<UserResponse> findUserById(@PathVariable(name = "id") String id) {
        return ApiResponse.<UserResponse>builder()
                .code(1001)
                .data(userService.getUserById(Integer.parseInt(id)))
                .build();
    }
}
