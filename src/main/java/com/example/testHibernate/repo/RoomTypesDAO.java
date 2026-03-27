package com.example.testHibernate.repo;

import com.example.testHibernate.entity.RoomTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypesDAO extends JpaRepository<RoomTypes,Integer> {
}
