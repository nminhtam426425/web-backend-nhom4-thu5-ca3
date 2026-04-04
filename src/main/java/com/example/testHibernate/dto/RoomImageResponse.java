package com.example.testHibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomImageResponse {
    Integer imageId;
    String imageUrl;
}
