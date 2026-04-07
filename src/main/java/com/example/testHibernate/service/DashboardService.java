package com.example.testHibernate.service;

import com.example.testHibernate.dto.DashboardResponse;
import com.example.testHibernate.dto.UserResponse;
import com.example.testHibernate.entity.Bookings;
import com.example.testHibernate.enums.BookingStatus;
import com.example.testHibernate.enums.RoomStatus;
import com.example.testHibernate.repo.BookingsDAO;
import com.example.testHibernate.repo.RoomsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DashboardService {
    @Autowired
    private BookingsDAO bookingsDAO;

    @Autowired
    private RoomsDAO roomsDAO;
    public DashboardResponse getDashboard(Integer branchId) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        // 1. totalCheckInToday
        Integer totalCheckInToday = bookingsDAO.countCheckInDay(
                branchId,
                BookingStatus.CHECKIN,
                startOfDay,
                endOfDay
        );

        // 2. totalRevenue today
        Double totalRevenue = bookingsDAO.sumRevenueByDate(
                branchId,
                BookingStatus.CHECKOUT,
                startOfDay,
                endOfDay
        );

        if (totalRevenue == null) totalRevenue = 0.0;

        // 3. totalRoom
        Long totalRoom = roomsDAO.countByBranch_BranchId(branchId);

        // 4. room status
        Long use = roomsDAO.countByBranch_BranchIdAndStatus(branchId, RoomStatus.CURRENTLY_OCCUPIED);
        Long needClean = roomsDAO.countByBranch_BranchIdAndStatus(branchId, RoomStatus.CLEARING_OUT);
        Long empty = roomsDAO.countByBranch_BranchIdAndStatus(branchId, RoomStatus.EMPTY);

        Long totalRoomForUser = use; // đang dùng

        Map<String,Long> rooms = new HashMap<>();
        rooms.put("use", use);
        rooms.put("needClean", needClean);
        rooms.put("empty", empty);

        // 5. newCustomer (top 5)
        List<Bookings> bookings = bookingsDAO.findTop5Customers(
                branchId,
                BookingStatus.CONFIRM,
                PageRequest.of(0, 5)
        );

        List<UserResponse> newCustomer = bookings.stream().map(b ->
                UserResponse.builder()
                        .userId(b.getCustomer().getUserId())
                        .fullName(b.getCustomer().getFullName())
                        .build()
        ).toList();

        // 6. revenue last 7 days
        List<Double> revenueLast7Day = new ArrayList<>();
        List<String> dayRevenue = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            LocalDateTime dayStart = startOfDay.minusDays(i);
            LocalDateTime dayEnd = dayStart.plusDays(1);

            Double revenue = bookingsDAO.sumRevenueByDate(
                    branchId,
                    BookingStatus.CHECKOUT,
                    dayStart,
                    dayEnd
            );

            revenueLast7Day.add(revenue != null ? revenue : 0.0);
            dayRevenue.add(getDayName(dayStart.getDayOfWeek()));
        }

        // đảo ngược list để đúng thứ tự
        Collections.reverse(revenueLast7Day);
        Collections.reverse(dayRevenue);

        return DashboardResponse.builder()
                .branchId(branchId)
                .totalCheckInToday(totalCheckInToday)
                .totalRoomForUser(totalRoomForUser)
                .totalRoom(totalRoom)
                .totalRevenue(totalRevenue)
                .rooms(rooms)
                .newCustomer(newCustomer)
                .revenueLast7Day(revenueLast7Day)
                .dayRevenue(dayRevenue)
                .build();
    }

    private String getDayName(DayOfWeek day) {
        switch (day) {
            case MONDAY: return "Thứ 2";
            case TUESDAY: return "Thứ 3";
            case WEDNESDAY: return "Thứ 4";
            case THURSDAY: return "Thứ 5";
            case FRIDAY: return "Thứ 6";
            case SATURDAY: return "Thứ 7";
            case SUNDAY: return "Chủ nhật";
        }
        return "";
    }
}
