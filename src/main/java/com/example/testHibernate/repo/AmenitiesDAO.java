package com.example.testHibernate.repo;

import com.example.testHibernate.entity.Amenities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenitiesDAO extends JpaRepository<Amenities,Integer> {
}
