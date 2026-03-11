package com.example.testHibernate.service;

import com.example.testHibernate.dto.UserResponse;
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
    @Autowired
    private UsersDAO usersDAO;

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

    public UserRequest saveUser(UserRequest user,boolean isSave) {
        Users newUser = new Users();
        if(!isSave)
            newUser.setId(user.getId());

        newUser.setName(user.getName());
        newUser.setPhone(user.getPhone());
        newUser.setEmail(user.getEmail());

        validateUser(user,isSave);
        userDao.save(newUser);

        Users userFounded = userDao.findUsersByPhone(newUser.getPhone());
        return UserRequest.builder()
                .id(userFounded.getId())
                .name(userFounded.getName())
                .phone(userFounded.getPhone())
                .email(userFounded.getEmail())
                .build();
    }

    public List<Users> findAllUsers() {
        return userDao.findAll();
    }

    public void deleteUser(UserRequest users) {
        userDao.deleteById(users.getId());
    }

    public UserResponse getUserById(int id){
        if(id < 1)
            throw new RuntimeException("Invalid Id !");
        List<Users> users = usersDAO.findAll();
        if(id >  users.size())
            throw new RuntimeException("Invalid Id !");
        Users user = users.get(id - 1);
        return UserResponse.builder()
                .id(id)
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }
}
