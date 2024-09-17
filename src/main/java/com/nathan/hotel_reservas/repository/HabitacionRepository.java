package com.nathan.hotel_reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nathan.hotel_reservas.models.HabitacionModel;

public interface HabitacionRepository extends JpaRepository<HabitacionModel, Long> {
    
}
