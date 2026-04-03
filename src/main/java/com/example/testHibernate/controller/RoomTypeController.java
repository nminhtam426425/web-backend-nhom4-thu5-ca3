package com.example.testHibernate.controller;

import com.example.testHibernate.dto.RoomTypeCreateRequest;
import com.example.testHibernate.dto.RoomTypeResponse;
import com.example.testHibernate.dto.RoomTypeUpdateRequest;
import com.example.testHibernate.entity.RoomTypes;
import com.example.testHibernate.response.ApiResponse;
import com.example.testHibernate.service.RoomTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000", "https://web-fontend-nhom4-thu5-ca3.vercel.app"})
@RestController
@RequestMapping("/roomtypes")
public class RoomTypeController {
    @Autowired
    private RoomTypesService roomTypesService;
    @GetMapping
    public ResponseEntity<List<RoomTypeResponse>>getAll(){
        List<RoomTypeResponse> roomTypes =  roomTypesService.getAll();
        return ResponseEntity.ok(roomTypes);
    }
    @PostMapping
    public ResponseEntity<RoomTypeResponse> create(@RequestBody RoomTypeCreateRequest rt){
        RoomTypeResponse createdRoomTypes= roomTypesService.create(rt);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoomTypes);
    }
    @PatchMapping("/{id}/baseprice-capacity")
    public ResponseEntity<RoomTypeResponse>updateBasePriceAndCapacity(
            @PathVariable Integer id,
            @RequestBody RoomTypeUpdateRequest req){
        RoomTypeResponse update = roomTypesService.updatePriceAndCapacity(id,req);
        return ResponseEntity.ok(update);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeResponse>update(@PathVariable Integer id,@RequestBody RoomTypeUpdateRequest rt){
        RoomTypeResponse updateRoomType = roomTypesService.update(id,rt);
        return ResponseEntity.ok(updateRoomType);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        roomTypesService.delete(id);
        return ResponseEntity.ok("Xóa loại phòng với id "+id+" thành công!");
    }
//    Truyền id chi nhánh trả về danh sách các loại phòng  tương ứng
    @GetMapping("/by-branch/{branchId}")
    public ResponseEntity<ApiResponse<List<RoomTypeResponse>>> getByBranch(@PathVariable Integer branchId){
        List<RoomTypeResponse> roomTypeResponses = roomTypesService.getRoomTypesByBranch(branchId);
        return ResponseEntity.ok(
                ApiResponse.<List<RoomTypeResponse>>builder()
                        .code(200)
                        .data(roomTypeResponses)
                        .build()
        );
    }
}
