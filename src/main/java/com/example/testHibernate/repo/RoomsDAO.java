package com.example.testHibernate.repo;

import com.example.testHibernate.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomsDAO extends JpaRepository<Rooms,Integer> {
}
