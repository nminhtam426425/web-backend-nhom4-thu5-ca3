package com.example.testHibernate.controller;

import com.example.testHibernate.dto.BookingServiceRequest;
import com.example.testHibernate.dto.BookingServiceResponse;
import com.example.testHibernate.response.ApiResponse;
import com.example.testHibernate.service.BookingServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://web-fontend-nhom4-thu5-ca3.vercel.app"})
@RestController
@RequestMapping("/booking-services")
public class BookingServicesController {
    @Autowired
    private BookingServicesService service;
    @GetMapping
    public ApiResponse<List<BookingServiceResponse>> getAll(){
        return ApiResponse.<List<BookingServiceResponse>>builder()
                .code(200)
                .data(service.getAll())
                .build();
    }
    @GetMapping("/{bookingId}")
    public ApiResponse<List<BookingServiceResponse>> getByBooking(@PathVariable Integer bookingId){
        return ApiResponse.<List<BookingServiceResponse>>builder()
                .code(200)
                .data(service.getByBooking(bookingId))
                .build();
    }
    @PostMapping
    public ApiResponse<BookingServiceResponse> create(@RequestBody BookingServiceRequest req){
        return ApiResponse.<BookingServiceResponse>builder()
                .code(200)
                .data(service.create(req))
                .message("Tạo service thành công")
                .build();
    }
    @PutMapping
    public ApiResponse<BookingServiceResponse> update(@RequestBody BookingServiceRequest req){
        return ApiResponse.<BookingServiceResponse>builder()
                .code(200)
                .data(service.update(req))
                .message("Cập nhật thành công")
                .build();
    }
    @DeleteMapping
    public ApiResponse<String> delete(
            @RequestParam Integer bookingId,
            @RequestParam String serviceId,
            @RequestParam String date
    ){
        service.delete(bookingId, serviceId, LocalDate.parse(date));

        return ApiResponse.<String>builder()
                .code(200)
                .message("Xóa thành công")
                .build();
    }
}
