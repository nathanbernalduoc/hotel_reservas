package com.nathan.hotel_reservas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nathan.hotel_reservas.models.HabitacionModel;
import com.nathan.hotel_reservas.repository.HabitacionRepository;

@Service
public class HabitacionServicelmpl implements HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;
    
    @Override
    public List<HabitacionModel> getAllHabitacion() {
        return habitacionRepository.findAll();
    }

    @Override
    public Optional<HabitacionModel> getHabitacionById(Long id) {
        return habitacionRepository.findById(id);
    }

    @Override
    public HabitacionModel createHabitacion(HabitacionModel habitacion) {
        return habitacionRepository.save(habitacion);
    }

    @Override
    public HabitacionModel updateHabitacion(Long id, HabitacionModel habitacion) {
        if (habitacionRepository.existsById(id)) {
            habitacion.setId(id);
            return habitacionRepository.save(habitacion);
        } else {
            return null;
        }
    }

    @Override
    public void deleteHabitacion(Long id) {
        habitacionRepository.deleteById(id);
    }
}
