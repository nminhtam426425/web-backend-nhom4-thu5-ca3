package com.example.testHibernate.service;

import com.example.testHibernate.dto.ServiceRequest;
import com.example.testHibernate.dto.ServiceResponse;
import com.example.testHibernate.entity.Services;
import com.example.testHibernate.repo.BookingServicesDAO;
import com.example.testHibernate.repo.ServicesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceServices {
    @Autowired
    private ServicesDAO servicesDAO;
    private ServiceResponse toResponse(Services s){
        return ServiceResponse.builder()
                .serviceId(s.getServiceId())
                .serviceName(s.getServiceName())
                .description(s.getDescription())
                .build();
    }
    public ServiceResponse create(ServiceRequest req){
        if(servicesDAO.existsById(req.getServiceId())){
            throw new RuntimeException("Service đã tồn tại");
        }
        Services s = new Services();
        s.setServiceId(req.getServiceId());
        s.setServiceName(req.getServiceName());
        s.setDescription(req.getDescription());

        return toResponse(servicesDAO.save(s));
    }

    public ServiceResponse update(String id, ServiceRequest req){
        Services s = servicesDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Service không tồn tại"));

        s.setServiceName(req.getServiceName());
        s.setDescription(req.getDescription());

        return toResponse(servicesDAO.save(s));
    }

    public List<ServiceResponse> getAll(){
        return servicesDAO.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void delete(String id, BookingServicesDAO bookingServicesDAO){
        boolean isUsed = bookingServicesDAO
                .findAll()
                .stream()
                .anyMatch(b -> b.getService().getServiceId().equals(id));

        if(isUsed){
            throw new RuntimeException("Service đang được sử dụng, không thể xóa");
        }

        servicesDAO.deleteById(id);
    }

}
