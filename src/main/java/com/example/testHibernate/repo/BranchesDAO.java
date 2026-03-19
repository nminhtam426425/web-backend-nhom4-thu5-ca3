package com.example.testHibernate.repo;

import com.example.testHibernate.entity.Branches;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchesDAO extends JpaRepository<Branches,Integer> {
    List<Branches> findByIsActiveTrue();
}
