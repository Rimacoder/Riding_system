package com.ridingsystem.repository;

import com.ridingsystem.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findBySourceIgnoreCaseAndDestinationIgnoreCaseAndTravelDate(String source, String destination, LocalDate travelDate);
}
