package com.example.testHibernate.service;

import com.example.testHibernate.dto.BookingServiceRequest;
import com.example.testHibernate.dto.BookingServiceResponse;
import com.example.testHibernate.entity.BookingServiceId;
import com.example.testHibernate.entity.BookingServices;
import com.example.testHibernate.entity.Bookings;
import com.example.testHibernate.entity.Services;
import com.example.testHibernate.repo.BookingServicesDAO;
import com.example.testHibernate.repo.BookingsDAO;
import com.example.testHibernate.repo.ServicesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServicesService {
    @Autowired
    private BookingServicesDAO bookingServicesDAO;
    @Autowired
    private BookingsDAO bookingsDAO;
    @Autowired
    private ServicesDAO servicesDAO;
    private BookingServiceResponse toResponse(BookingServices b){
        return BookingServiceResponse.builder()
                .bookingId(b.getBooking().getBookingId())
                .serviceId(b.getService().getServiceId())
                .dateToUse(b.getDateToUse())
                .timeServiceStart(b.getTimeServiceStart())
                .serviceName(b.getServiceName())
                .price(b.getPrice())
                .description(b.getDescription())
                .build();
    }

    public List<BookingServiceResponse> getAll(){
        return bookingServicesDAO.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<BookingServiceResponse> getByBooking(Integer bookingId){
        return bookingServicesDAO.findByBooking_BookingId(bookingId)
                .stream()
                .map(this::toResponse)
                .toList();
    }
    public BookingServiceResponse create(BookingServiceRequest req){
        Bookings booking = bookingsDAO.findById(req.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Services service = servicesDAO.findById(req.getServiceId())
                .orElseGet(()-> {
                    Services newService = new Services();
                    newService.setServiceId(req.getServiceId());
                    newService.setServiceName(req.getServiceName());
                    newService.setDescription(req.getDescription());
                    return servicesDAO.save(newService);
                });

        BookingServices b = new BookingServices();
        b.setBooking(booking);
        b.setService(service);
        b.setDateToUse(req.getDateToUse());
        b.setTimeServiceStart(req.getTimeServiceStart());
        b.setServiceName(req.getServiceName());
        b.setPrice(req.getPrice());
        b.setDescription(req.getDescription());

        return toResponse(bookingServicesDAO.save(b));
    }
    public BookingServiceResponse update(BookingServiceRequest req){
        BookingServiceId id = new BookingServiceId();
        id.setBooking(req.getBookingId());
        id.setService(req.getServiceId());
        id.setDateToUse(req.getDateToUse());

        BookingServices b = bookingServicesDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Service không tồn tại"));

        b.setTimeServiceStart(req.getTimeServiceStart());
        b.setServiceName(req.getServiceName());
        b.setPrice(req.getPrice());
        b.setDescription(req.getDescription());

        return toResponse(bookingServicesDAO.save(b));
    }
    public void delete(Integer bookingId, String serviceId, java.time.LocalDate date){
        BookingServiceId id = new BookingServiceId();
        id.setBooking(bookingId);
        id.setService(serviceId);
        id.setDateToUse(date);

        if(!bookingServicesDAO.existsById(id)){
            throw new RuntimeException("Không tồn tại service");
        }

        bookingServicesDAO.deleteById(id);
    }
}
