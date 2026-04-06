package com.example.testHibernate.controller;

import com.example.testHibernate.dto.DashboardResponse;
import com.example.testHibernate.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/{branchId}")
    public Map<String, Object> getDashboard(@PathVariable Integer branchId) {

        DashboardResponse data = dashboardService.getDashboard(branchId);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("data", data);

        return response;
    }
}