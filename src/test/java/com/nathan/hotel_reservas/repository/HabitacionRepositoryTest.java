package com.nathan.hotel_reservas.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.nathan.hotel_reservas.models.HabitacionModel;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class HabitacionRepositoryTest {
    @Autowired
    private HabitacionRepository habitacionRepository;

    @Test
    public void guardarHabitacionTest() {
        HabitacionModel habitacion = new HabitacionModel();
        habitacion.setHabitacionNumero("A22");
        habitacion.setCamas(3);
        habitacion.setPiso(4);
        habitacion.setObservacion("Cama de prueba para test unitarios");

        HabitacionModel result = habitacionRepository.save(habitacion);

        assertNotNull(result.getId());
        assertEquals(3, result.getCamas());

    }

}
