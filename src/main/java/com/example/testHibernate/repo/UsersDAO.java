package com.example.testHibernate.repo;

import com.example.testHibernate.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersDAO extends JpaRepository<Users, String> {
    boolean existsUsersByPhone(String phone);

    Users findUsersByPhone(String phone);
    List<Users> findByRoleId(Integer roleId);
}
