package com.example.testHibernate.repo;

import com.example.testHibernate.entity.RoomImages;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomImagesDAO extends JpaRepository<RoomImages,Integer> {
    List<RoomImages> findByRoomType_TypeId(Integer typeId);
    @Modifying
    @Transactional
    @Query("DELETE FROM RoomImages ri WHERE ri.roomType.typeId = :typeId")
    void deleteByRoomType_TypeId(@Param("typeId") Integer typeId);
}
