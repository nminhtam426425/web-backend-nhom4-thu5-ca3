package com.example.testHibernate.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookingBranchResponse {
    private Integer branchId;
    private String branchName;
    private String address;
    private List<BookingResponse> bookings;
}
