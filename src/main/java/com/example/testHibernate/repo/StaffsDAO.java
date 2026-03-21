package com.example.testHibernate.repo;

import com.example.testHibernate.entity.Staffs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffsDAO extends JpaRepository<Staffs,String> {
}
