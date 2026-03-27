package com.example.testHibernate.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BookingStatus {
    PENDING("chờ xác nhận"),
    CONFIRM("đã xác nhận"),
    CHECKIN("đã check-in"),
    CHECKOUT("đã check-out"),
    CANCEL("đã hủy");
    private final String value;
    BookingStatus(String value){
        this.value = value;
    }
    @JsonValue
    public String getValue(){
        return value;
    }
    @JsonCreator
    public static BookingStatus fromValue(String value){
        for (BookingStatus status : BookingStatus.values()){
            if(status.value.equalsIgnoreCase(value)){
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: "+value);
    }
    @Override
    public String toString(){
        return value;
    }
}
