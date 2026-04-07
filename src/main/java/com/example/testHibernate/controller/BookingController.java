package com.example.testHibernate.controller;

import com.example.testHibernate.dto.BookingDetailResponse;
import com.example.testHibernate.dto.BookingResponse;
import com.example.testHibernate.dto.CustomerBookingResponse;
import com.example.testHibernate.enums.BookingStatus;
import com.example.testHibernate.response.ApiResponse;
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
    public ResponseEntity<List<BookingResponse>> getAll(){
        List<BookingResponse> bookings = bookingsService.getAll();
        return ResponseEntity.ok(bookings);
    }
//    API tìm và lọc theo điều kiện
    @GetMapping("/search")
    public ResponseEntity<List<BookingResponse>>searchBookings(
            @RequestParam(required = false) Integer branchId,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false)BookingStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime toDate
            ){
        List<BookingResponse> filterBookings = bookingsService.filterBookings(branchId,customerId,status,fromDate,toDate);
        return ResponseEntity.ok(filterBookings);
    }
    @GetMapping("/customer")
    public ApiResponse<CustomerBookingResponse> getByCustomer(@RequestParam String customerId){
        if(customerId == null || customerId.isBlank()){
            throw new RuntimeException("customerId không hợp lệ");
        }
        return ApiResponse.<CustomerBookingResponse>builder()
                .code(1001)
                .data(bookingsService.getByCustomer(customerId))
                .build();
    }
    @GetMapping("/detail/{id}")
    public ApiResponse<BookingDetailResponse> getDetail(@PathVariable Integer id){
        return ApiResponse.<BookingDetailResponse>builder()
                .code(1001)
                .data(bookingsService.getDetail(id))
                .build();
    }
    @GetMapping("/status")
    public ApiResponse<List<BookingResponse>> getByStatus(@RequestParam String status){
        BookingStatus bookingStatus = BookingStatus.fromValue(status);
        return ApiResponse.<List<BookingResponse>>builder()
                .code(1001)
                .data(bookingsService.getByStatus(bookingStatus))
                .build();
    }
}
