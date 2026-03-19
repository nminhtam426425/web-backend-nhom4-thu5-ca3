package com.example.testHibernate.service;

import com.example.testHibernate.entity.Branches;
import com.example.testHibernate.repo.BranchesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchesServices {
    @Autowired
    private BranchesDAO repo;
    public List<Branches> getAll(){
        return  repo.findByIsActiveTrue();
    }
    public Branches create(Branches b){
        return repo.save(b);
    }
    public Branches update(Integer id,Branches newB){
        Branches b = repo.findById(id).orElseThrow(()-> new RuntimeException("Not Found"));
        b.setBranchName(newB.getBranchName());
        b.setAddress(newB.getAddress());
        b.setPhone(newB.getPhone());
        b.setEmail(newB.getEmail());
        b.setDescription(newB.getDescription());
        return repo.save(b);
    }
    public void delete(Integer id){
        repo.deleteById(id);
    }
    public void hide(Integer id){
        Branches b = repo.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
        b.setIsActive(false);
        repo.save(b);
    }
}
