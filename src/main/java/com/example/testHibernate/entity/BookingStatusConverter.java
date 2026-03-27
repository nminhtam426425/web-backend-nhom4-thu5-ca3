package com.example.testHibernate.entity;

import com.example.testHibernate.enums.BookingStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BookingStatusConverter implements AttributeConverter<BookingStatus,String> {
    @Override
    public String convertToDatabaseColumn(BookingStatus status){
        return status != null ? status.getValue(): null;
    }
    @Override
    public BookingStatus convertToEntityAttribute(String dbData){
        return BookingStatus.fromValue(dbData);
    }
}
