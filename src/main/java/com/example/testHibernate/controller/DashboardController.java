package com.example.testHibernate.controller;

import com.example.testHibernate.dto.DashboardResponse;
import com.example.testHibernate.response.ApiResponse;
import com.example.testHibernate.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000", "https://web-fontend-nhom4-thu5-ca3.vercel.app"})
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/{branchId}")
    public ApiResponse<DashboardResponse> getDashboard(@PathVariable Integer branchId) {
        return ApiResponse.<DashboardResponse>builder()
                .data(dashboardService.getDashboard(branchId))
                .code(200)
                .build();
    }
}