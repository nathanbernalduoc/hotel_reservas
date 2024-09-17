package com.nathan.hotel_reservas.service;

import java.util.List;
import java.util.Optional;

import com.nathan.hotel_reservas.models.HabitacionModel;

public interface  HabitacionService {

    List<HabitacionModel> getAllHabitacion();
    Optional<HabitacionModel> getHabitacionById(Long id);
    
    HabitacionModel createHabitacion(HabitacionModel usuario);
    HabitacionModel updateHabitacion(Long id, HabitacionModel usuario);
    void deleteHabitacion(Long id);

}
