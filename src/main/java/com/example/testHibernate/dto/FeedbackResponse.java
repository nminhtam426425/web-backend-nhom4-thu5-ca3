package com.example.testHibernate.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedbackResponse {
    private Integer rating;
    private String comment;
}
