package com.example.testHibernate.repo;

import com.example.testHibernate.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookingsDAO extends JpaRepository<Bookings,Integer>, JpaSpecificationExecutor<Bookings> {
}
