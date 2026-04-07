package com.example.testHibernate.service;

import com.example.testHibernate.dto.*;
import com.example.testHibernate.entity.BookingDetails;
import com.example.testHibernate.entity.Bookings;
import com.example.testHibernate.entity.Users;
import com.example.testHibernate.enums.BookingStatus;
import com.example.testHibernate.repo.BookingsDAO;
import com.example.testHibernate.repo.StaffsDAO;
import com.example.testHibernate.repo.UsersDAO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingsService {
    @Autowired
    private BookingsDAO bookingsDAO;
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private StaffsDAO staffsDAO;
    private UserResponse mapUser(Users u) {
        Double totalSpent = bookingsDAO.sumPriceByCustomer(u.getUserId());
        Integer branchId = staffsDAO.findById(u.getUserId()).map(staff -> staff.getBranchId())
                .orElse(null);
        return UserResponse.builder()
                .userId(u.getUserId())
                .username(u.getUsername())
                .fullName(u.getFullName())
                .email(u.getEmail())
                .phone(u.getPhone())
                .address(u.getAddress())
                .roleId(u.getRoleId())
                .isActive(u.getIsActive())
                .alreadySpent(totalSpent)
                .branchId(branchId)
                .build();
    }

    private BookingResponse toResponse(Bookings b) {
        return BookingResponse.builder()
                .bookingId(b.getBookingId())
                .bookingCode(b.getBookingCode())
                .customer(mapUser(b.getCustomer()))
                .staffId(b.getStaff() != null ? b.getStaff().getUserId() : null)
                .branchId(b.getBranch() != null ? b.getBranch().getBranchId() : null)
                .branchName(b.getBranch() != null ? b.getBranch().getBranchName() : null)
                .roomTypeId(b.getRoomType() != null ? b.getRoomType().getTypeId() : null)
                .roomTypeName(b.getRoomType() != null ? b.getRoomType().getTypeName() : null)
                .checkInDate(b.getCheckInDate())
                .checkOutDate(b.getCheckOutDate())
                .priceAtBooking(b.getPriceAtBooking())
                .status(b.getStatus() != null ? b.getStatus().getValue() : null)
                .note(b.getNote())
                .createdAt(b.getCreatedAt())
                .build();
    }
    public List<BookingResponse>getAll(){
        return bookingsDAO.findAll()
                .stream().filter(b->b.getCustomer() != null)
                .map(this::toResponse)
                .toList();
    }
//    Tìm và lọc booking theo nhiều điều kiện linh hoạt
    public List<BookingResponse>filterBookings(Integer branchId, String customerId,
                                        BookingStatus status, LocalDateTime fromDate,LocalDateTime toDate){
        Specification<Bookings> spec = (root, query, criteriaBuilder) ->{
            List<Predicate> predicates =  new ArrayList<>();
            if(branchId != null){
                predicates.add(criteriaBuilder.equal(root.get("branch").get("branchId"),branchId));
            }
            if(customerId != null && !customerId.isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("customer").get("userId"),customerId));
            }
            if(status != null){
                predicates.add(criteriaBuilder.equal(root.get("status"),status));
            }
            if(fromDate != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("checkInDate"),fromDate));
            }
            if(toDate != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("checkInDate"),toDate));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return bookingsDAO.findAll(spec).stream().map(this::toResponse).toList();
    }
    public CustomerBookingResponse getByCustomer(String customerId){
        List<Bookings> list = bookingsDAO.findByCustomer_UserIdWithRoom(customerId);
        if(list.isEmpty()) throw  new RuntimeException("Không có booking");
        Users u = list.get(0).getCustomer();
        double total = list.stream()
                .map(b -> b.getPriceAtBooking() != null ? b.getPriceAtBooking().doubleValue() : 0)
                .reduce(0.0,Double::sum);
        List<RoomRentResponse> rooms = list.stream().map(
                b -> {
                    String roomNumber = null;
                    if(b.getBookingDetails() != null && !b.getBookingDetails().isEmpty()){
                        BookingDetails bd = b.getBookingDetails().get(0);
                        if(bd.getRoom() !=null){
                            roomNumber = bd.getRoom().getRoomNumber();
                        }
                    }
                    return RoomRentResponse.builder()
                            .bookingId(b.getBookingId())
                            .roomNumber(roomNumber)
                            .typeName(b.getRoomType() != null ? b.getRoomType().getTypeName() : null)
                            .checkIn(b.getCheckInDate())
                            .checkOut(b.getCheckOutDate())
                            .rating(5)
                            .alreadySpent(b.getPriceAtBooking() != null ? b.getPriceAtBooking().doubleValue() :0)
                            .build();
                }).toList();
        return CustomerBookingResponse.builder()
                .name(u.getFullName())
                .phone(u.getPhone())
                .email(u.getEmail())
                .alreadySpent(total)
                .roomRent(rooms)
                .build();
    }
    public BookingDetailResponse getDetail(Integer bookingId){
        Bookings b = bookingsDAO.findById(bookingId)
                .orElseThrow(()-> new RuntimeException("Booking not found"));
        Users u = b.getCustomer();
        String roomNumber = null;
        if(b.getBookingDetails() != null && !b.getBookingDetails().isEmpty()){
            BookingDetails bd = b.getBookingDetails().get(0);
            if(bd.getRoom() != null){
                roomNumber = bd.getRoom().getRoomNumber();
            }
        }
        String staffName = null;
        if(b.getStaff() != null){
            staffName =usersDAO.findById(b.getStaff().getUserId())
                    .map(Users::getFullName)
                    .orElse(null);
        }
        return BookingDetailResponse.builder()
                .name(u.getFullName())
                .phone(u.getPhone())
                .email(u.getEmail())

                .priceAtBooking(b.getPriceAtBooking() != null ? b.getPriceAtBooking().doubleValue() : 0)
                .typeName(b.getRoomType() != null ? b.getRoomType().getTypeName() : null)

                .roomNumber(roomNumber)

                .staffName(staffName)

                .checkIn(b.getCheckInDate())
                .checkOut(b.getCheckOutDate())

                .dateOrder(b.getCreatedAt())
                .dateConfirm(b.getCheckInDate()) // nếu có field confirm riêng thì đổi

                .feedback(FeedbackResponse.builder()
                        .rating(5)
                        .comment("Tốt")
                        .build())

                .build();
    }
    public List<BookingResponse> getByStatus(BookingStatus status){
        return bookingsDAO.findByStatusOrderByCreatedAtDesc(status)
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
