package com.example.testHibernate.controller;

import com.example.testHibernate.entity.Bookings;
import com.example.testHibernate.enums.BookingStatus;
import com.example.testHibernate.service.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://web-fontend-nhom4-thu5-ca3.vercel.app"})
@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingsService bookingsService;
    @GetMapping
    public ResponseEntity<List<Bookings>> getAll(){
        List<Bookings> bookings = bookingsService.getAll();
        return ResponseEntity.ok(bookings);
    }
//    API tìm và lọc theo điều kiện
    @GetMapping("/search")
    public ResponseEntity<List<Bookings>>searchBookings(
            @RequestParam(required = false) Integer branchId,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false)BookingStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime toDate
            ){
        List<Bookings> filterBookings = bookingsService.filterBookings(branchId,customerId,status,fromDate,toDate);
        return ResponseEntity.ok(filterBookings);
    }
}
