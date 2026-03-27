package com.example.testHibernate.service;

import com.example.testHibernate.entity.Bookings;
import com.example.testHibernate.enums.BookingStatus;
import com.example.testHibernate.repo.BookingsDAO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingsService {
    @Autowired
    private BookingsDAO bookingsDAO;
    public List<Bookings>getAll(){
        return bookingsDAO.findAll();
    }
//    Tìm và lọc booking theo nhiều điều kiện linh hoạt
    public List<Bookings>filterBookings(Integer branchId, String customerId,
                                        BookingStatus status, LocalDateTime fromDate,LocalDateTime toDate){
        Specification<Bookings> spec = (root, query, criteriaBuilder) ->{
            List<Predicate> predicates =  new ArrayList<>();
            if(branchId != null){
                predicates.add(criteriaBuilder.equal(root.get("branch").get("branchId"),branchId));
            }
            if(customerId != null && !customerId.isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("customer").get("userId"),customerId));
            }
            if(status != null){
                predicates.add(criteriaBuilder.equal(root.get("status"),status));
            }
            if(fromDate != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("checkInDate"),fromDate));
            }
            if(toDate != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("checkInDate"),toDate));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return bookingsDAO.findAll(spec);
    }
}
