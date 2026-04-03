package com.example.testHibernate.repo;

import com.example.testHibernate.entity.Users;
import com.example.testHibernate.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersDAO extends JpaRepository<Users, String> {
    boolean existsUsersByPhone(String phone);

    Users findUsersByPhone(String phone);
    List<Users> findByRoleId(Integer roleId);
    List<Users> findByIsActiveFalse();
    @Query("SELECT COALESCE(SUM(b.priceAtBooking),0) " +
            "FROM Bookings b " +
            "WHERE b.customer.userId =:userId " +
            "AND b.status = status")
    Double getTotalSpentByUserId(@Param("userId") String userId, @Param("status") BookingStatus status);
}
