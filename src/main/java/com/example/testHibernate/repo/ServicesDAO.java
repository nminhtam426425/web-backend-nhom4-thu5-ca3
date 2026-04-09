package com.example.testHibernate.repo;

import com.example.testHibernate.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesDAO extends JpaRepository<Services,String> {

}
