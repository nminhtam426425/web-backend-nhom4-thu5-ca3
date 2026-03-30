package com.example.testHibernate.repo;

import com.example.testHibernate.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomsDAO extends JpaRepository<Rooms,Integer> {
    @Query("SELECT r.branch.branchId,COUNT(r.typeId) FROM Rooms r GROUP BY r.branch.branchId")
    List<Object[]>countRoomsByBranch();
}
