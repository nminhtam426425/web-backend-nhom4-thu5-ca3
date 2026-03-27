package com.example.testHibernate.repo;

import com.example.testHibernate.entity.RoomImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomImagesDAO extends JpaRepository<RoomImages,Integer> {
    List<RoomImages> findByRoomType_TypeId(Integer typeId);
}
