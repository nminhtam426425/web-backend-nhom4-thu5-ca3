package com.example.testHibernate.entity;

import com.example.testHibernate.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;
    @Column(name = "booking_code",columnDefinition = "varchar(20)")
    private String bookingCode;
    //    Quan hệ với bảng users
    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "user_id")
    private Users customer;
    //    Quan hệ với bảng staff
    @ManyToOne
    @JoinColumn(name = "staff_confirm",referencedColumnName = "user_id")
    private Staffs staff;
    //    Quan hệ với bảng room_types
    @ManyToOne
    @JoinColumn(name = "type_room",referencedColumnName = "type_id")
    private RoomTypes roomType;
    //    Quan hệ với bảng branches
    @ManyToOne
    @JoinColumn(name = "branch_id",referencedColumnName = "branch_id")
    private Branches branch;
    @Column(name = "check_in_date")
    private LocalDateTime checkInDate;
    @Column(name = "check_out_date")
    private LocalDateTime checkOutDate;
    @Column(name = "actual_check_in")
    private LocalDateTime actualCheckIn;
    @Column(name = "actual_check_out")
    private LocalDateTime actualCheckOut;
    @Column(name = "prica_at_booking",precision = 15,scale = 2)
    private BigDecimal priceAtBooking;
    @Column(name = "status")
    @Convert(converter = BookingStatusConverter.class)
    private BookingStatus status;
    @Column(name = "note",columnDefinition = "text")
    private String note;
    @Column(name = "created_at",insertable = false,updatable = false)
    private java.sql.Timestamp createdAt;

}
