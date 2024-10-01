package com.nathan.hotel_reservas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HabitacionNotFoundExcepcion extends RuntimeException {

    public HabitacionNotFoundExcepcion(String message) {
        super(message);
    }
}
