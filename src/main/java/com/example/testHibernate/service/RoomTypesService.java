package com.example.testHibernate.service;

import com.example.testHibernate.dto.*;
import com.example.testHibernate.entity.*;
import com.example.testHibernate.enums.BookingStatus;
import com.example.testHibernate.repo.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RoomTypesService {
    @Autowired
    private RoomTypesDAO roomTypesDAO;
    @Autowired
    private RoomImagesDAO roomImagesDAO;
    @Autowired
    private RoomTypeBranchesDAO roomTypeBranchesDAO;
    @Autowired
    private BranchesDAO branchesDAO;
    @Autowired
    private AmenitiesDAO amenitiesDAO;
    @Autowired
    private RoomsDAO roomsDAO;
    @Transactional
    public List<RoomTypeResponse>getAll(){
        List<RoomTypes> roomTypes = roomTypesDAO.findAllWithImages();
        return roomTypes.stream().map(rt->{
            List<String> images = rt.getImages().stream()
                    .map(RoomImages::getImageUrl).toList();
            List<Integer> branchIds = rt.getBranches() != null
                    ? rt.getBranches().stream()
                    .map(Branches::getBranchId).toList()
                    : List.of();
            List<AmenityResponse> amenities = rt.getAmenities() != null
                    ? rt.getAmenities().stream()
                    .map(a -> new AmenityResponse(
                            a.getIdAmenities(),
                            a.getDescription())).toList() : List.of();
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
                    .images(images)
                    .branchIds(branchIds)
                    .amenities(amenities)
                    .build();
        }).toList();
    }
    @Transactional
    public List<RoomTypeResponse> getRoomTypesByBranch(Integer branchId){
        return roomTypeBranchesDAO.findRoomTypesByBranchId(branchId)
                .stream()
                .map(rt->{
                    List<String> images = roomImagesDAO
                            .findByRoomType_TypeId(rt.getTypeId())
                            .stream()
                            .map(RoomImages::getImageUrl)
                            .toList();
                    List<Integer> branchIds = rt.getBranches() !=null ? rt.getBranches()
                            .stream().map(Branches::getBranchId).toList():List.of();
                    List<AmenityResponse> amenities = rt.getAmenities() != null
                            ? rt.getAmenities().stream()
                            .map(a -> new AmenityResponse(
                                    a.getIdAmenities(),
                                    a.getDescription()
                            )).toList()
                            : List.of();
                    Integer totalRooms = roomsDAO.countRoomsByTypeAndBranch(rt.getTypeId(),branchId);
                    Double revenue = roomTypesDAO.getRevenueByTypeAndBranch(
                            rt.getTypeId(),
                            branchId,
                            BookingStatus.CHECKOUT
                    );
                    List<Rooms> rooms = roomsDAO
                            .findByRoomTypes_TypeIdAndBranch_BranchId(rt.getTypeId(), branchId);

                    List<RoomResponse> roomResponses = rooms.stream().map(r ->
                            RoomResponse.builder()
                                    .id(r.getRoomId())
                                    .numberRoom(r.getRoomNumber())
                                    .status(r.getStatus()).checkIn("-").checkOut("-").build()).toList();
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
                            .images(images)
                            .branchIds(branchIds)
                            .amenities(amenities)
                            .totalRooms(totalRooms)
                            .revenue(revenue)
                            .rooms(roomResponses)
                            .build();
                }).toList();
    }
    public RoomTypeResponse create(RoomTypeCreateRequest req) {
        if (req.getTypeName() == null || req.getTypeName().trim().isEmpty()) {
            throw new RuntimeException("Tên loại phòng không được để trống");
        }
        RoomTypes rt = new RoomTypes();
        rt.setTypeName(req.getTypeName());
        rt.setDescriptionRoom(req.getDescriptionRoom());
        rt.setBasePrice(req.getBasePrice());
        rt.setPriceSundayNormal(req.getPriceSundayNormal());
        rt.setPricePeakSeason(req.getPricePeakSeason());
        rt.setPricePeakSunday(req.getPricePeakSunday());
        rt.setPriceHour(req.getPriceHour());
        rt.setCapacity(req.getCapacity());
        RoomTypes savedRoomTypes = roomTypesDAO.save(rt);
        List<String>imageUrls = new ArrayList<>();
        if (req.getImages() != null) {
            for (String url : req.getImages()) {
                RoomImages img = new RoomImages();
                img.setImageUrl(url);
                img.setRoomType(savedRoomTypes);
                roomImagesDAO.save(img);
                imageUrls.add(url);
            }
        }
        List<Integer> branchIds = new ArrayList<>();
        if (req.getBranchIds() != null) {
            List<Branches> branches = branchesDAO.findAllById(req.getBranchIds());
            savedRoomTypes.setBranches(branches);
            branchIds = branches.stream().map(Branches::getBranchId).toList();
        }
        List<AmenityResponse> amenities = new ArrayList<>();
        if(req.getAmenities() != null){
            List<Amenities> amenitiesList = amenitiesDAO.findAllById(req.getAmenities());
            savedRoomTypes.setAmenities(amenitiesList);
            amenities = amenitiesList.stream().map(a -> new AmenityResponse(
                    a.getIdAmenities(),
                    a.getDescription()
            )).toList();
        }
        return RoomTypeResponse.builder()
                .typeId(savedRoomTypes.getTypeId())
                .typeName(savedRoomTypes.getTypeName())
                .descriptionRoom(savedRoomTypes.getDescriptionRoom())
                .basePrice(savedRoomTypes.getBasePrice())
                .priceSundayNormal(savedRoomTypes.getPriceSundayNormal())
                .pricePeakSeason(savedRoomTypes.getPricePeakSeason())
                .pricePeakSunday(savedRoomTypes.getPricePeakSunday())
                .priceHour(savedRoomTypes.getPriceHour())
                .capacity(savedRoomTypes.getCapacity())
                .images(imageUrls)
                .branchIds(branchIds)
                .amenities(amenities)
                .build();
    }
        @Transactional
        public RoomTypeResponse update(Integer id,RoomTypeUpdateRequest req){
            RoomTypes rt = roomTypesDAO.findById(id).orElseThrow(()->new RuntimeException("RoomType not found"));
            if (req.getTypeName() != null && !req.getTypeName().trim().isEmpty()) {
                rt.setTypeName(req.getTypeName());
            }

            if (req.getDescriptionRoom() != null) {
                rt.setDescriptionRoom(req.getDescriptionRoom());
            }

            if (req.getBasePrice() != null) {
                rt.setBasePrice(req.getBasePrice());
            }

            if (req.getPriceSundayNormal() != null) {
                rt.setPriceSundayNormal(req.getPriceSundayNormal());
            }

            if (req.getPricePeakSeason() != null) {
                rt.setPricePeakSeason(req.getPricePeakSeason());
            }

            if (req.getPricePeakSunday() != null) {
                rt.setPricePeakSunday(req.getPricePeakSunday());
            }

            if (req.getPriceHour() != null) {
                rt.setPriceHour(req.getPriceHour());
            }

            if (req.getCapacity() != null) {
                rt.setCapacity(req.getCapacity());
            }
            List<String> imageUrls = new ArrayList<>();
            if(req.getImages() != null){
                roomImagesDAO.deleteByRoomType_TypeId(id);
                rt.setImages(new ArrayList<>());
                List<RoomImages> newImages = new ArrayList<>();
                for (String url : req.getImages()){
                    RoomImages img = new RoomImages();
                    img.setImageUrl(url);
                    img.setRoomType(rt);
                    newImages.add(img);
                    imageUrls.add(url);
                }
                rt.setImages(newImages);
            }else{
                imageUrls = rt.getImages() !=null
                        ? rt.getImages().stream().map(RoomImages::getImageUrl).toList(): List.of();
            }
            List<Integer> branchIds = new ArrayList<>();

            if (req.getBranchIds() != null) {
                List<Branches> branches = branchesDAO.findAllById(req.getBranchIds());
                rt.setBranches(branches);

                branchIds = branches.stream()
                        .map(Branches::getBranchId)
                        .toList();
            } else {
                branchIds = rt.getBranches() != null
                        ? rt.getBranches().stream().map(Branches::getBranchId).toList()
                        : List.of();
            }
            List<AmenityResponse> amenities = new ArrayList<>();
            if(req.getAmenities() != null){
                List<Amenities> amenitiesList = amenitiesDAO.findAllById(req.getAmenities());
                rt.setAmenities(amenitiesList);
                amenities = amenitiesList.stream()
                        .map(a -> new AmenityResponse(
                                a.getIdAmenities(),
                                a.getDescription()
                        ))
                        .toList();
            }else{
                amenities = rt.getAmenities() != null
                        ? rt.getAmenities().stream().map(a -> new AmenityResponse(
                                a.getIdAmenities(),
                                a.getDescription()
                        )).toList() : List.of();
            }
            RoomTypes updated = roomTypesDAO.save(rt);

            return RoomTypeResponse.builder()
                    .typeId(updated.getTypeId())
                    .typeName(updated.getTypeName())
                    .descriptionRoom(updated.getDescriptionRoom())
                    .basePrice(updated.getBasePrice())
                    .priceSundayNormal(updated.getPriceSundayNormal())
                    .pricePeakSeason(updated.getPricePeakSeason())
                    .pricePeakSunday(updated.getPricePeakSunday())
                    .priceHour(updated.getPriceHour())
                    .capacity(updated.getCapacity())
                    .images(imageUrls)
                    .branchIds(branchIds)
                    .amenities(amenities)
                    .build();
        }
    public void delete(Integer id){
        roomTypesDAO.deleteById(id);
    }
//    Trộn 2 nhiệm vụ cập nhật giá gốc và số lượng khách tối đa
    public RoomTypeResponse updatePriceAndCapacity(Integer id, RoomTypeUpdateRequest req){
        RoomTypes rt = roomTypesDAO.findById(id).orElseThrow(()->new RuntimeException("RoomType not found"));
        if(req.getBasePrice() !=null){
            rt.setBasePrice(req.getBasePrice());
        }
        if(req.getCapacity() != null){
            rt.setCapacity(req.getCapacity());
        }
        RoomTypes updated = roomTypesDAO.save(rt);

        // map images
        List<String> images = updated.getImages() != null
                ? updated.getImages().stream()
                .map(RoomImages::getImageUrl)
                .toList()
                : List.of();
        List<AmenityResponse> amenities = rt.getAmenities() != null
                ? rt.getAmenities().stream()
                .map(a -> new AmenityResponse(
                        a.getIdAmenities(),
                        a.getDescription()
                )).toList()
                : List.of();
        // map branches
        List<Integer> branchIds = updated.getBranches() != null
                ? updated.getBranches().stream()
                .map(Branches::getBranchId)
                .toList()
                : List.of();

        // return DTO
        return RoomTypeResponse.builder()
                .typeId(updated.getTypeId())
                .typeName(updated.getTypeName())
                .descriptionRoom(updated.getDescriptionRoom())
                .basePrice(updated.getBasePrice())
                .priceSundayNormal(updated.getPriceSundayNormal())
                .pricePeakSeason(updated.getPricePeakSeason())
                .pricePeakSunday(updated.getPricePeakSunday())
                .priceHour(updated.getPriceHour())
                .capacity(updated.getCapacity())
                .images(images)
                .branchIds(branchIds)
                .amenities(amenities)
                .build();
    }
}
