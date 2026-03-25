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
    private StaffsDAO staffsDAO;

    private boolean validateName(String name){
        return name != null && !name.trim().isEmpty();
    }

    private boolean validatePhone(String phone){
        return phone != null && phone.matches("\\d{10}");
    }

    private void validateUser(UserRequest users,boolean isSave) {
        if(!validateName(users.getFullName()))
            throw new RuntimeException("Tên không được để rỗng !");

        if(!validatePhone(users.getPhone()))
            throw new RuntimeException("Định dạng số điện thoại không hợp lệ !");

        if(userDao.existsUsersByPhone(users.getPhone()) && isSave)
            throw new RuntimeException("Số điện thoại đã tồn tại !");
    }

    public UserRequest saveUser(UserRequest user,boolean isSave) {
        validateUser(user,isSave);

        Users entity;
        if(isSave){
            entity = new Users();
        }else {
            String id = user.getId().trim();
            entity = userDao.findById(id).orElseThrow(()->new RuntimeException("User not exist"));
        }
            entity.setUserId(user.getId());

        // map FULL field
       entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setFullName(user.getFullName());
        entity.setEmail(user.getEmail());
        entity.setPhone(user.getPhone());
        entity.setAddress(user.getAddress());
        entity.setRoleId(user.getRoleId());

        validateUser(user,isSave);
        userDao.save(entity);

        Users savedUser = userDao.findUsersByPhone(entity.getPhone());
        if(savedUser == null){
            throw new RuntimeException("Không tìm thấy user sau khi lưu !");
        }
        return UserRequest.builder()
                .id(savedUser.getUserId())
                .username(savedUser.getUsername())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .address(savedUser.getAddress())
                .roleId(savedUser.getRoleId())
                .isActive(savedUser.getIsActive())
                .build();
    }

    public List<Users> findAllUsers() {
        return userDao.findAll();
    }

    public void deleteUser(UserRequest users) {
        userDao.deleteById(users.getId());
    }

    public UserResponse getUserById(String id){
        Users user = userDao.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        return UserResponse.builder()
                .id(id)
                .fullName(user.getFullName())
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
        if(user.getFullName() == null || user.getFullName().trim().isEmpty()){
            throw new RuntimeException("Tên không được bỏ trống");
        }
        String phone = user.getPhone();
        if(phone != null){
            phone = phone.trim();
        }
        if (phone == null || !phone.matches("^\\d{10}$")){
            throw new RuntimeException("Số điện thoại không hợp lệ");
        }
        if(userDao.existsUsersByPhone(user.getPhone())){
            throw new RuntimeException("Số điện thoại đã tồn tại");
        }
        if(user.getPassword() == null || user.getPassword().trim().isEmpty()){
            throw new RuntimeException("Password không được để trống");
        }
        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());
        newUser.setPhone(phone);
        newUser.setEmail(user.getEmail());
        newUser.setAddress(user.getAddress());
        newUser.setRoleId(Role.STAFF.getValue());
        newUser.setIsActive(true);
        Users saveUser = userDao.save(newUser);

        Staffs staff = new Staffs();
        staff.setUserId(saveUser.getUserId());
        staff.setBranchId(branchId);
        staffsDAO.save(staff);
    }
}
