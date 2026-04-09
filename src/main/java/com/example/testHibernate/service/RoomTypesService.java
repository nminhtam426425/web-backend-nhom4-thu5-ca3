package com.example.testHibernate.service;

import com.example.testHibernate.dto.*;
import com.example.testHibernate.entity.*;
import com.example.testHibernate.enums.BookingStatus;
import com.example.testHibernate.enums.RoomStatus;
import com.example.testHibernate.repo.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    @Autowired
    private BookingsDAO bookingsDAO;
    @Transactional
    public List<RoomTypeResponse>getAll(){
        List<RoomTypes> roomTypes = roomTypesDAO.findAllWithImages();
        return roomTypes.stream().map(rt->{
            List<RoomImageResponse> images = rt.getImages() != null
                    ? rt.getImages().stream()
                    .map(img -> new RoomImageResponse(
                            img.getImageId(),
                            img.getImageUrl()
                    )).toList()
                    : List.of();
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
                    List<RoomImageResponse> images = roomImagesDAO
                            .findByRoomType_TypeId(rt.getTypeId())
                            .stream()
                            .map(r -> new RoomImageResponse(
                                    r.getImageId(),
                                    r.getImageUrl()
                            ))
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
                    Long totalRooms = roomsDAO.countRoomsByTypeAndBranch(rt.getTypeId(),branchId);
                    Double revenue = roomTypesDAO.getRevenueByTypeAndBranch(
                            rt.getTypeId(),
                            branchId,
                            BookingStatus.CHECKOUT
                    );
                    List<Rooms> rooms = roomsDAO
                            .findByRoomTypes_TypeIdAndBranch_BranchId(rt.getTypeId(), branchId);

                    List<RoomResponse> roomResponses = rooms.stream().map(r ->{
                        LocalDateTime checkIn = null;
                        LocalDateTime checkOut = null;

                            List<Bookings> bookings = bookingsDAO.findByRoomIdAndStatuses(
                                    r.getRoomId(),
                                    List.of(BookingStatus.CHECKIN,BookingStatus.CHECKOUT)
                            );
                            if(!bookings.isEmpty()){
                                Bookings b = bookings.get(0);
                                checkIn = b.getActualCheckIn() != null
                                        ? b.getActualCheckIn() : b.getCheckInDate();
                                checkOut = b.getActualCheckOut() != null ?
                                        b.getActualCheckOut() : b.getCheckOutDate() ;
                            }

                          return   RoomResponse.builder()
                                    .id(r.getRoomId())
                                    .numberRoom(r.getRoomNumber())
                                    .status(r.getStatus())
                                    .checkIn(checkIn)
                                    .checkOut(checkOut)
                                    .build();
                                    }).toList();
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
                            .totalRooms(totalRooms != null ? totalRooms : 0L)
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
        List<RoomImageResponse>images = new ArrayList<>();
        if (req.getImages() != null) {
            for (String url : req.getImages()) {
                RoomImages img = new RoomImages();
                img.setImageUrl(url);
                img.setRoomType(savedRoomTypes);
                RoomImages savedImg = roomImagesDAO.save(img);
                roomImagesDAO.save(img);
                images.add(new RoomImageResponse(
                        savedImg.getImageId(),
                        savedImg.getImageUrl()
                ));
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
        savedRoomTypes = roomTypesDAO.save(savedRoomTypes);
        Long totalRooms = 0L;
        Double revenue = 0.0;
        List<RoomResponse> roomResponses = new ArrayList<>();
        if(!branchIds.isEmpty()){
            Integer branchId = branchIds.get(0);
            totalRooms = roomsDAO.countRoomsByTypeAndBranch(
                    savedRoomTypes.getTypeId(),
                    branchId
            );
            revenue = roomTypesDAO.getRevenueByTypeAndBranch(
                    savedRoomTypes.getTypeId(), branchId,
                    BookingStatus.CHECKOUT
            );
            List<Rooms> rooms = roomsDAO
                    .findByRoomTypes_TypeIdAndBranch_BranchId(
                            savedRoomTypes.getTypeId(),
                            branchId
                    );
            roomResponses = rooms.stream().map(
                    r -> {
                        LocalDateTime checkIn = null;
                        LocalDateTime checkOut = null;

                        List<Bookings> bookings = bookingsDAO.findByRoomIdAndStatuses(
                                r.getRoomId(),
                                List.of(BookingStatus.CHECKIN, BookingStatus.CHECKOUT)
                        );

                        if (!bookings.isEmpty()) {
                            Bookings b = bookings.get(0);

                            checkIn = b.getActualCheckIn() != null
                                    ? b.getActualCheckIn()
                                    : b.getCheckInDate();

                            checkOut = b.getActualCheckOut() != null
                                    ? b.getActualCheckOut()
                                    : b.getCheckOutDate();
                        }
                       return RoomResponse.builder()
                                .id(r.getRoomId())
                                .numberRoom(r.getRoomNumber())
                                .status(r.getStatus())
                                .checkIn(checkIn)
                                .checkOut(checkOut)
                                .build();
                    }).toList();
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
                .images(images)
                .branchIds(branchIds)
                .amenities(amenities)
                .totalRooms(totalRooms)
                .revenue(revenue)
                .rooms(roomResponses)
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
            List<RoomImageResponse> images = new ArrayList<>();
            if(req.getImages() != null){
                roomImagesDAO.deleteByRoomType_TypeId(id);
                rt.setImages(new ArrayList<>());
                List<RoomImages> newImages = new ArrayList<>();
                for (String url : req.getImages()){
                    RoomImages img = new RoomImages();
                    img.setImageUrl(url);
                    img.setRoomType(rt);
                    newImages.add(img);
                }
                rt.setImages(newImages);
                images = newImages.stream()
                        .map(i -> new RoomImageResponse(
                                i.getImageId(),
                                i.getImageUrl()
                        )).toList();
            }else{
                images = rt.getImages() != null
                        ? rt.getImages().stream()
                        .map(i -> new RoomImageResponse(
                                i.getImageId(),
                                i.getImageUrl()
                        )).toList()
                        : List.of();
            }

            List<Integer> branchIds = new ArrayList<>();

            if (req.getBranchIds() != null) {
                List<Branches> branches = branchesDAO.findAllById(req.getBranchIds());
                rt.getBranches().clear();
                rt.getBranches().addAll(branches);

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
                rt.getAmenities().clear();
                rt.getAmenities().addAll(amenitiesList);
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
                    .images(images)
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
        List<RoomImageResponse> images = updated.getImages() != null
                ? updated.getImages().stream()
                .map(i -> new RoomImageResponse(
                        i.getImageId(),
                        i.getImageUrl()
                ))
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
