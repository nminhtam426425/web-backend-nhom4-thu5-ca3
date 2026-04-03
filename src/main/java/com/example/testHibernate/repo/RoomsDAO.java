package com.example.testHibernate.repo;

import com.example.testHibernate.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomsDAO extends JpaRepository<Rooms,Integer> {
    @Query("SELECT r.branch.branchId,COUNT(r) FROM Rooms r GROUP BY r.branch.branchId")
    List<Object[]>countRoomsByBranch();
    @Query("SELECT COUNT(r) FROM Rooms r " +
            "WHERE r.roomTypes.typeId = :typeId " +
            "AND r.branch.branchId = :branchId")
    Integer countRoomsByTypeAndBranch(@Param("typeId") Integer typeId,@Param("branchId") Integer branchId);
    List<Rooms> findByRoomTypes_TypeIdAndBranch_BranchId(Integer typeId, Integer branchId);
}
