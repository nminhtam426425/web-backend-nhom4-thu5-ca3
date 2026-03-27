package com.example.testHibernate.repo;

import com.example.testHibernate.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomsDAO extends JpaRepository<Rooms,Integer> {
    @Query("SELECT COUNT(r) FROM Rooms r WHERE r.branch.branchId = :branchId")
    Long countByBranchId(Integer branchId);
}
