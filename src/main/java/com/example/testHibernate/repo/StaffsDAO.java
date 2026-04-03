package com.example.testHibernate.repo;

import com.example.testHibernate.entity.Staffs;
import com.example.testHibernate.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StaffsDAO extends JpaRepository<Staffs,String> {
    @Query("SELECT u FROM Users u JOIN Staffs s On u.userId = s.userId WHERE s.branchId = :branchId and u.isActive = true")
    List<Users> findStaffByBranchId(Integer branchId);
}
