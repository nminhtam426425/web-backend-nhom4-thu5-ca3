package com.example.testHibernate.repo;

import com.example.testHibernate.entity.RoomTypes;
import com.example.testHibernate.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomTypesDAO extends JpaRepository<RoomTypes,Integer> {
    @Query("SELECT DISTINCT rt FROM RoomTypes rt LEFT JOIN FETCH rt.images")
    List<RoomTypes>findAllWithImages();
    @Query("SELECT COALESCE(SUM(b.priceAtBooking),0) " +
            "FROM Bookings b " +
            "WHERE b.roomType.typeId = :typeId " +
            "AND b.branch.branchId = :branchId " +
            "AND b.status = :status")
    Double getRevenueByTypeAndBranch(@Param("typeId") Integer typeId, @Param("branchId") Integer branchId, @Param("status")BookingStatus status);
    List<RoomTypes> findByAmenities_IdAmenities(Integer amenityId);
}
