package com.example.testHibernate.service;

import com.example.testHibernate.entity.Amenities;
import com.example.testHibernate.repo.AmenitiesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmenitiesService {
    @Autowired
    AmenitiesDAO amenitiesDAO;
    public List<Amenities>getAll(){
        return amenitiesDAO.findAll();
    }
    public Amenities create(Amenities a){
        return amenitiesDAO.save(a);
    }
    public Amenities update(Integer id,Amenities newA){
        Amenities a = amenitiesDAO.findById(id).orElseThrow(()->new RuntimeException("Amenity not found"));
        a.setDescription(newA.getDescription());
        return amenitiesDAO.save(a);
    }
    public void delete(Integer id){
        amenitiesDAO.deleteById(id);
    }

}
