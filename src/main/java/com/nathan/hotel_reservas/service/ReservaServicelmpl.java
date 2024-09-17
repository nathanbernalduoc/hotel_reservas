package com.nathan.hotel_reservas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nathan.hotel_reservas.models.ReservaModel;
import com.nathan.hotel_reservas.repository.ReservaRepository;

@Service
public class ReservaServicelmpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    
    @Override
    public List<ReservaModel> getAllReserva() {
        return reservaRepository.findAll();
    }

    @Override
    public Optional<ReservaModel> getReservaById(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    public ReservaModel createReserva(ReservaModel reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public ReservaModel updateReserva(Long id, ReservaModel reserva) {
        if (reservaRepository.existsById(id)) {
            //reserva.setId(id);
            return reservaRepository.save(reserva);
        } else {
            return null;
        }
    }

    @Override
    public void deleteReserva(Long id) {
        reservaRepository.deleteById(id);
    }
}
