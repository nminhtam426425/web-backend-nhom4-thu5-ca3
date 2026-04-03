package com.example.testHibernate.repo;

import com.example.testHibernate.entity.RoomAmenitites;
import com.example.testHibernate.entity.RoomTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomAmenititesDAO extends JpaRepository<RoomAmenitites,Integer> {
    @Query("SELECT DISTINCT rt FROM RoomTypes rt JOIN FETCH rt.amenities a WHERE a.idAmenities =:idAmenities")
    List<RoomTypes>findRoomTypesByIdAmenities(@Param("idAmenities") Integer idAmenities);
}
