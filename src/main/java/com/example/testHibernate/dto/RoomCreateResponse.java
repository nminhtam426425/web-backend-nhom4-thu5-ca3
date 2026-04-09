package com.example.testHibernate.dto;

import com.example.testHibernate.enums.RoomStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomCreateResponse {
    private Integer roomId;
    private String roomNumber;

    private Integer typeId;
    private String typeName;

    private Integer branchId;
    private String branchName;

    private RoomStatus status;
}
