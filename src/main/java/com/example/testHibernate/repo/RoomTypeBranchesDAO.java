package com.example.testHibernate.repo;

import com.example.testHibernate.entity.RoomTypeBranches;
import com.example.testHibernate.entity.RoomTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomTypeBranchesDAO extends JpaRepository<RoomTypeBranches,Integer> {
    @Query("SELECT DISTINCT rt FROM RoomTypes rt " +
            "JOIN FETCH rt.branches b " +
            "WHERE b.branchId = :branchId")
    List<RoomTypes> findRoomTypesByBranchId(@Param("branchId") Integer branchId);
}
