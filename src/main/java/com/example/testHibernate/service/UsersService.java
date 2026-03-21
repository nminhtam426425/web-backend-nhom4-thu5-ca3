package com.example.testHibernate.service;

import com.example.testHibernate.dto.UserResponse;
import com.example.testHibernate.entity.Staffs;
import com.example.testHibernate.entity.Users;
import com.example.testHibernate.enums.Role;
import com.example.testHibernate.repo.StaffsDAO;
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
    @Autowired
    private StaffsDAO staffsDAO;

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
//    Hàm khóa tài khoản khách (Bảo)
    public void disableUser(String id){
        Users user = userDao.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        user.setIsActive(false);
        userDao.save(user);
    }
//    Hàm mở tài khoản khách (Bảo)
    public void enableUser(String id){
        Users user = userDao.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        user.setIsActive(true);
        userDao.save(user);
    }
    //    Hàm khóa tài khoản nhân viên (Bảo)
    public void disableStaff(String id){
        Users user = userDao.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        if(user.getRoleId() != 2 || !staffsDAO.existsById(id)){
            throw new RuntimeException("Đây là khách hàng,không phải nhân viên");
        }
        user.setIsActive(false);
        userDao.save(user);
    }
    //    Hàm mở tài khoản nhân viên (Bảo)
    public void enableStaff(String id){
        Users user = userDao.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        if(user.getRoleId() != 2 || !staffsDAO.existsById(id)){
            throw new RuntimeException("Đây là khách hàng,không phải nhân viên");
        }
        user.setIsActive(true);
        userDao.save(user);
    }
//    Hàm tìm theo Role
    public List<Users> findByRoleId(Integer roleId){
        return userDao.findByRoleId(roleId);
    }
//    Hàm lọc tất cả nhân viên (Bảo)
    public List<Users> findAllStaffs(){
        return findByRoleId(Role.STAFF.getValue());
    }
//    Hàm tạo tài khoản nhân viên (Bảo)
    public void createStaff(UserRequest user,Integer branchId){
        Users newUser = new Users();
        newUser.setName(user.getName());
        newUser.setPhone(user.getPhone());
        newUser.setEmail(user.getEmail());
        newUser.setRoleId(Role.STAFF.getValue());
        Users saveUser = userDao.save(newUser);

        Staffs staff = new Staffs();
        staff.setUserId(saveUser.getId());
        staff.setBranchId(branchId);
        staffsDAO.save(staff);
    }
}
