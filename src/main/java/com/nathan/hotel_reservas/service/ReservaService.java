package com.nathan.hotel_reservas.service;

import java.util.List;
import java.util.Optional;

import com.nathan.hotel_reservas.models.ReservaModel;

public interface  ReservaService {

    List<ReservaModel> getAllReserva();
    Optional<ReservaModel> getReservaById(Long id);
    
    ReservaModel createReserva(ReservaModel usuario);
    ReservaModel updateReserva(Long id, ReservaModel usuario);
    void deleteReserva(Long id);

}
