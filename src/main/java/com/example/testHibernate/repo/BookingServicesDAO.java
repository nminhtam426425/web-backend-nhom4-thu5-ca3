package com.example.testHibernate.repo;

import com.example.testHibernate.entity.BookingServiceId;
import com.example.testHibernate.entity.BookingServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingServicesDAO extends JpaRepository<BookingServices, BookingServiceId> {
    List<BookingServices> findByBooking_BookingId(Integer bookingId);
}