package com.example.testHibernate.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoomStatus {
//    trống
    EMPTY("trống"),
//    đã đặt
    BOOKED("đã đặt"),
//    đang ở
    CURRENTLY_OCCUPIED("đang ở"),
//    đang dọn
    CLEARING_OUT("đang dọn"),
//    bảo trì
    MAINTENANCE("bảo trì");
    private  final String value;
    RoomStatus(String value){
        this.value = value;
    }
    @JsonValue
    public String getValue(){
        return value;
    }
    @JsonCreator
    public static RoomStatus fromValue(String value) {
        for (RoomStatus status : RoomStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + value);
    }
    @Override
    public String toString(){
        return value;
    }
}
