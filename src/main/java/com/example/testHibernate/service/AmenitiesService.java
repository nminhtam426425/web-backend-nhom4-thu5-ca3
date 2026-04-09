package com.example.testHibernate.service;

import com.example.testHibernate.dto.AmenityResponse;
import com.example.testHibernate.entity.Amenities;
import com.example.testHibernate.entity.RoomTypes;
import com.example.testHibernate.repo.AmenitiesDAO;
import com.example.testHibernate.repo.RoomTypesDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmenitiesService {
    @Autowired
    AmenitiesDAO amenitiesDAO;
    @Autowired
    private RoomTypesDAO roomTypesDAO;
    private AmenityResponse toResponse(Amenities a){
        return AmenityResponse.builder()
                .idAmenities(a.getIdAmenities())
                .description(a.getDescription())
                .build();
    }
    public List<AmenityResponse>getAll(){
        List<Amenities> amenities = amenitiesDAO.findAll();
        return amenities.stream().map(
                this::toResponse).toList();
    }
    public AmenityResponse create(Amenities a){
        if(a.getDescription() == null || a.getDescription().trim().isEmpty()){
            throw  new RuntimeException("Mô tả không được để trống");
        }
        Amenities savedAmenities = amenitiesDAO.save(a);
        return toResponse(savedAmenities);
    }
    public AmenityResponse update(Integer id,Amenities newA){
        Amenities a = amenitiesDAO.findById(id).orElseThrow(()->new RuntimeException("Amenity not found"));
        if(newA.getDescription() != null){
            a.setDescription(newA.getDescription());
        }
        Amenities updatedAmenities = amenitiesDAO.save(a);
        return toResponse(updatedAmenities);
    }
    @Transactional
    public void delete(Integer id) {
        Amenities amenity = amenitiesDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Amenity not found"));
        List<RoomTypes> roomTypes = roomTypesDAO.findByAmenities_IdAmenities(id);
        for (RoomTypes rt : roomTypes) {
            rt.getAmenities().remove(amenity);
        }
        roomTypesDAO.saveAll(roomTypes);
        amenitiesDAO.delete(amenity);
    }
}
