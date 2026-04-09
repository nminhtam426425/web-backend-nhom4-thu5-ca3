package com.example.testHibernate.repo;

import com.example.testHibernate.entity.Feedbacks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbacksDAO extends JpaRepository<Feedbacks, Integer> {
    Feedbacks findByBooking_BookingId(Integer bookingId);
}
