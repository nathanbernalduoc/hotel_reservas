package com.nathan.hotel_reservas.repository;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.nathan.hotel_reservas.models.ReservaModel;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class ReservaRepositoryTest {
    @Autowired
    private ReservaRepository reservaRepository;

    @Test
    public void reservarTest() {
        ReservaModel reserva = new ReservaModel();
        reserva.setHabitacionId("A2");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
        Date inicio = null;
        Date termino = null;
        try {
            inicio = formatter.parse("10/01/2024");
            termino = formatter.parse("12/01/2024");
        } catch (ParseException e) {e.printStackTrace();}

        reserva.setInicio(inicio);
        reserva.setTermino(termino);
        reserva.setCliente("Fernando Bernal");
        reserva.setReservaFlag(1);

        ReservaModel result = reservaRepository.save(reserva);

        assertNotNull(result.getReservaId());
        assertEquals("A2", result.getHabitacionId());

    }

}
