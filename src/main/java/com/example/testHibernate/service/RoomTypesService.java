package com.example.testHibernate.service;

import com.example.testHibernate.dto.RoomTypeResponse;
import com.example.testHibernate.dto.RoomTypeUpdateRequest;
import com.example.testHibernate.entity.RoomTypes;
import com.example.testHibernate.repo.RoomImagesDAO;
import com.example.testHibernate.repo.RoomTypesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoomTypesService {
    @Autowired
    private RoomTypesDAO roomTypesDAO;
    @Autowired
    private RoomImagesDAO roomImagesDAO;
    public List<RoomTypeResponse>getAll(){
        List<RoomTypes> roomTypes = roomTypesDAO.findAll();
        return roomTypes.stream().map(rt->{
            List<String> images = roomImagesDAO.findByRoomType_TypeId(rt.getTypeId()).stream()
                    .map(img->img.getImageUrl()).toList();
            return RoomTypeResponse.builder()
                    .typeId(rt.getTypeId())
                    .typeName(rt.getTypeName())
                    .descriptionRoom(rt.getDescriptionRoom())
                    .basePrice(rt.getBasePrice())
                    .priceSundayNormal(rt.getPriceSundayNormal())
                    .pricePeakSeason(rt.getPricePeakSeason())
                    .pricePeakSunday(rt.getPricePeakSunday())
                    .priceHour(rt.getPriceHour())
                    .capacity(rt.getCapacity())
                    .images(images) // 🔥 add ảnh vào đây
                    .build();
        }).toList();
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
