package com.nathan.hotel_reservas.controllers;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Locale;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nathan.hotel_reservas.models.ReservaModel;
import com.nathan.hotel_reservas.service.ReservaServicelmpl;


@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaServicelmpl reservaServicelmpl;

    @Test
    public void getAllTestHatoaes() throws Exception {
        //Arrange
        // Reserva creation
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date inicio = formatter.parse("2024-10-01");
        Date fin = formatter.parse("2024-10-30");

        ReservaModel reserva1 = new ReservaModel();
        reserva1.setCliente("Nathan Bernal #1");
        reserva1.setHabitacionId("A2");
        reserva1.setInicio(inicio);
        reserva1.setTermino(fin);

        ReservaModel reserva2 = new ReservaModel();
        reserva2.setCliente("Lulú Bernal");
        reserva2.setHabitacionId("A1");
        reserva2.setInicio(inicio);
        reserva2.setTermino(fin);

        List<ReservaModel> reservas = Arrays.asList(reserva1, reserva2);

        when(reservaServicelmpl.getAllReservas()). thenReturn(reservas);

        // Act && Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/reserva", "list"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.aMapWithSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.reservaList[0].titulo", Matchers.is("Testing new pelicula #99")))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.reservaList[1].titulo", Matchers.is("Testing new pelicula #100")));
    }

    @Test
    public void getReservaIdTest() throws Exception {
        //Arrange
        //String titulo = "Oppenheimer";

        // Act && Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/reserva", "1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.aMapWithSize(1)));

    }
    
}
