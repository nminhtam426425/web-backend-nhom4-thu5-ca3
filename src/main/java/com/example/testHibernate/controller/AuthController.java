package com.example.testHibernate.controller;

import com.example.testHibernate.entity.Users;
import com.example.testHibernate.jwt.JwtUtils;
import com.example.testHibernate.repo.UsersDAO;
import com.example.testHibernate.service.UsersService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://web-fontend-nhom4-thu5-ca3.vercel.app"
})
public class AuthController {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private UsersService usersService;
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> req) {

        String username = req.get("username");
        String password = req.get("password");

        Users user = usersDAO.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        String encodedPass = usersService.encodeMD5(password);

        if (!user.getPassword().equals(encodedPass)) {
            throw new RuntimeException("Sai mật khẩu");
        }

        String role = "USER";
        if (user.getRoleId() == 1) {
            role = "ADMIN";
        }

        String token = JwtUtils.generateToken(
               user.getUserId(),
                role
        );

        String refreshToken = JwtUtils.generateRefreshToken(
                user.getUserId(),
                role
        );

        Map<String, String> res = new HashMap<>();
        res.put("token", token);
        res.put("refreshToken", refreshToken);

        return res;
    }
    @GetMapping("/refresh")
    public Map<String, String> refresh(@RequestParam String refreshToken) {

        if (!JwtUtils.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh token không hợp lệ");
        }

        Claims claims = JwtUtils.getAllClaims(refreshToken);

        String userId = claims.get("userId", String.class);
        String role = claims.get("role", String.class);

        String newToken = JwtUtils.generateToken(userId, role);

        Map<String, String> res = new HashMap<>();
        res.put("token", newToken);

        return res;
    }
}