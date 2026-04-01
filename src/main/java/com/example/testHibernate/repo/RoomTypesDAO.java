package com.example.testHibernate.repo;

import com.example.testHibernate.entity.RoomTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomTypesDAO extends JpaRepository<RoomTypes,Integer> {
    @Query("SELECT DISTINCT rt FROM RoomTypes rt LEFT JOIN FETCH rt.images")
    List<RoomTypes>findAllWithImages();
}
