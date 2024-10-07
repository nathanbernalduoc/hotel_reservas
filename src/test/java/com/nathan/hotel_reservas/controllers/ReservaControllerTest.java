package com.nathan.hotel_reservas.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nathan.hotel_reservas.models.ReservaModel;
import com.nathan.hotel_reservas.controllers.ReservaController;
import com.nathan.hotel_reservas.service.ReservaServicelmpl;
import com.nathan.hotel_reservas.service.HabitacionServicelmpl;

@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaServicelmpl reservaServiceMock;

    @MockBean
    private HabitacionServicelmpl HabitacionServiceMock;

    @Test
    public void obtenerTodoTest() throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
        Date inicio = null;
        Date termino = null;
        try {
            inicio = formatter.parse("10/01/2024");
            termino = formatter.parse("12/01/2024");
        } catch (ParseException e) {e.printStackTrace();}
        
        ReservaModel reserva1 = new ReservaModel();
        reserva1.setCliente("Juan Perez");
        reserva1.setHabitacionId("A2");
        reserva1.setInicio(inicio);
        reserva1.setTermino(termino);
        reserva1.setReservaFlag(1);

        try {
            inicio = formatter.parse("03/04/2024");
            termino = formatter.parse("05/04/2024");
        } catch (ParseException e) {e.printStackTrace();}

        ReservaModel reserva2 = new ReservaModel();
        reserva2.setCliente("Pedro González");
        reserva2.setHabitacionId("A3");
        reserva2.setInicio(inicio);
        reserva2.setTermino(termino);
        reserva2.setReservaFlag(1);

        List<ReservaModel> reservas = Arrays.asList(reserva1, reserva2);
        when(reservaServiceMock.getAllReservas()).thenReturn(reservas);

        mockMvc.perform(MockMvcRequestBuilders.get("/reserva"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.reservaModelList[0].cliente", Matchers.is("Juan Perez")))
            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.reservaModelList[0].habitacionId", Matchers.is("A2")));
    }

}
