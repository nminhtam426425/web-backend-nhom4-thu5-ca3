package com.example.testHibernate.service;

import com.example.testHibernate.dto.RoomTypeUpdateRequest;
import com.example.testHibernate.entity.RoomTypes;
import com.example.testHibernate.repo.RoomTypesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoomTypesService {
    @Autowired
    private RoomTypesDAO roomTypesDAO;
    public List<RoomTypes>getAll(){
        return roomTypesDAO.findAll();
    }
    public RoomTypes create(RoomTypes rt){
        if(rt.getTypeName()== null || rt.getTypeName().trim().isEmpty()){
            throw new RuntimeException("Tên loại phòng không được để trống");
        }
        return roomTypesDAO.save(rt);
    }
    public RoomTypes update(Integer id,RoomTypes rtDetails){
        RoomTypes rt = roomTypesDAO.findById(id).orElseThrow(()->new RuntimeException("RoomType not found"));
        rt.setTypeName(rtDetails.getTypeName());
        rt.setDescriptionRoom(rtDetails.getDescriptionRoom());
        rt.setBasePrice(rtDetails.getBasePrice());
        rt.setPriceSundayNormal(rtDetails.getPriceSundayNormal());
        rt.setPricePeakSeason(rtDetails.getPricePeakSeason());
        rt.setPricePeakSunday(rtDetails.getPricePeakSunday());
        rt.setPriceHour(rtDetails.getPriceHour());
        rt.setCapacity(rtDetails.getCapacity());
        return roomTypesDAO.save(rt);
    }
    public void delete(Integer id){
        roomTypesDAO.deleteById(id);
    }
//    Trộn 2 nhiệm vụ cập nhật giá gốc và số lượng khách tối đa
    public RoomTypes updatePriceAndCapacity(Integer id, RoomTypeUpdateRequest req){
        RoomTypes rt = roomTypesDAO.findById(id).orElseThrow(()->new RuntimeException("RoomType not found"));
        if(req.getBasePrice() !=null){
            rt.setBasePrice(req.getBasePrice());
        }
        if(req.getCapacity() != null){
            rt.setCapacity(req.getCapacity());
        }
        return roomTypesDAO.save(rt);
    }
}
