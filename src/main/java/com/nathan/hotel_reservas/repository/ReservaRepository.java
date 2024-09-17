package com.nathan.hotel_reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nathan.hotel_reservas.models.ReservaModel;

public interface ReservaRepository extends JpaRepository<ReservaModel, Long> {
    
}
