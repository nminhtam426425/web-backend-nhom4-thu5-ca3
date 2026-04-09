package com.example.testHibernate.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "feedbacks")
@Data
public class Feedbacks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Integer feedbackId;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Bookings booking;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Users customer;

    private Integer rating;
    private String comment;

    @Column(name = "created_at")
    private java.sql.Timestamp createdAt;
}
