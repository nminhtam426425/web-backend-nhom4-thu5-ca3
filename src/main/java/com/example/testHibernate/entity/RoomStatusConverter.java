package com.example.testHibernate.entity;

import com.example.testHibernate.enums.RoomStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoomStatusConverter implements AttributeConverter<RoomStatus,String> {
    @Override
    public String convertToDatabaseColumn(RoomStatus status){
        return status != null ? status.getValue(): null;
    }
    @Override
    public RoomStatus convertToEntityAttribute(String dbData){
        return RoomStatus.fromValue(dbData);
    }
}
