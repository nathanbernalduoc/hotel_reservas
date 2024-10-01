package com.nathan.hotel_reservas.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nathan.hotel_reservas.models.ReservaModel;
import com.nathan.hotel_reservas.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {
    @InjectMocks
    private ReservaServicelmpl reservaServicio;
    @Mock
    private ReservaRepository reservaRepositoryMock;

    @Test
    public void guardarReservaTest() throws ParseException {

        ReservaModel reserva = new ReservaModel();
        reserva.setCliente("Cliente de prueba");
        reserva.setHabitacionId("A1");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date fechaInicio = (Date) formatter.parse("01-JAN-2024");
        Date fechaTermino = (Date) formatter.parse("02-JAN-2024");

        reserva.setInicio(fechaInicio);
        reserva.setTermino(fechaTermino);
        
        when(reservaRepositoryMock.save(any())).thenReturn(reserva);

        ReservaModel result = reservaServicio.createReserva(reserva);

        assertEquals("Cliente de prueba", result.getCliente());

    }
}