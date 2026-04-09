package com.example.testHibernate.dto;

import lombok.Data;

@Data
public class ServiceRequest {
    private String serviceId;
    private String serviceName;
    private String description;
}