package com.example.testHibernate.service;

import com.example.testHibernate.entity.Users;
import com.example.testHibernate.repo.UsersDAO;
import com.example.testHibernate.dto.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService{
    @Autowired
    private UsersDAO userDao;

    private boolean validateName(String name){
        return !name.isEmpty();
    }

    private boolean validatePhone(String phone){
        return phone.length() == 10;
    }

    private void validateUser(UserRequest users,boolean isSave) {
        if(!validateName(users.getName()))
            throw new RuntimeException("Tên không được để rỗng !");

        if(!validatePhone(users.getPhone()))
            throw new RuntimeException("Định dạng số điện thoại không hợp lệ !");

        if(userDao.existsUsersByPhone(users.getPhone()) && isSave)
            throw new RuntimeException("Số điện thoại đã tồn tại !");
    }

    public void saveUser(UserRequest user,boolean isSave) {
        Users newUser = new Users();
        if(!isSave)
            newUser.setId(user.getId());

        newUser.setName(user.getName());
        newUser.setPhone(user.getPhone());
        newUser.setEmail(user.getEmail());

        validateUser(user,isSave);
        userDao.save(newUser);
    }

    public List<Users> findAllUsers() {
        return userDao.findAll();
    }

    public void deleteUser(UserRequest users) {
        userDao.deleteById(users.getId());
    }
}
