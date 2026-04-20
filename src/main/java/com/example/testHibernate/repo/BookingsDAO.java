package com.example.testHibernate.repo;

import com.example.testHibernate.entity.Bookings;
import com.example.testHibernate.enums.BookingStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingsDAO extends JpaRepository<Bookings,Integer>, JpaSpecificationExecutor<Bookings> {
    @Query("SELECT COUNT(b) " +
            "FROM Bookings b " +
            "WHERE b.branch.branchId = :branchId " +
            "AND b.status = :status " +
            "AND b.actualCheckIn >= :startOfDay " +
            "AND b.actualCheckIn < :endOfDay")
    Integer countCheckInDay(Integer branchId, BookingStatus status, LocalDateTime startOfDay,LocalDateTime endOfDay);
    @Query("SELECT SUM(b.priceAtBooking) " +
            "FROM Bookings b " +
            "WHERE b.branch.branchId = :branchId " +
            "AND b.status = :status " +
            "AND b.actualCheckOut >= :startOfDay " +
            "AND b.actualCheckOut < :endOfDay")
    Double sumRevenueByDate(
            Integer branchId,
            BookingStatus status,
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );
    @Query("SELECT SUM(b.priceAtBooking) " +
            "FROM Bookings b " +
            "WHERE b.branch.branchId = :branchId " +
            "AND b.status = :status")
    Double sumTotalRevenue(Integer branchId, BookingStatus status);
    @Query("SELECT b " +
            "FROM Bookings b " +
            "WHERE b.branch.branchId = :branchId " +
            "AND b.status IN (:statuses) " +
            "ORDER BY b.createdAt DESC")
    List<Bookings> findTop5NewCustomers(@Param("branchId") Integer branchId,@Param("statuses") List<BookingStatus>  statuses, Pageable pageable);
    @Query("SELECT b FROM Bookings b WHERE b.customer IS NOT NULL")
    List<Bookings> findAllWithCustomer();
    @Query("SELECT b FROM Bookings b " +
            "LEFT JOIN FETCH b.bookingDetails bd " +
            "LEFT JOIN FETCH bd.room " +
            "WHERE b.customer.userId = :userId")
    List<Bookings> findByCustomer_UserIdWithRoom(String userId);
    List<Bookings> findByStatusOrderByCreatedAtDesc(BookingStatus status);
    @Query("SELECT SUM(b.priceAtBooking) FROM Bookings b WHERE b.customer.userId = :userId")
    Double sumPriceByCustomer(String userId);
    @Query("SELECT b FROM Bookings b " +
            "JOIN b.bookingDetails bd " +
            "WHERE bd.room.roomId = :roomId " +
            "AND b.status IN :statuses " +
            "ORDER BY b.actualCheckIn DESC")
    List<Bookings> findByRoomIdAndStatuses(Integer roomId, List<BookingStatus> statuses);
}
