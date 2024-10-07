package com.nathan.hotel_reservas.controllers;

import java.util.Date;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nathan.hotel_reservas.models.HabitacionModel;
import com.nathan.hotel_reservas.models.ReservaModel;
import com.nathan.hotel_reservas.service.HabitacionService;
import com.nathan.hotel_reservas.service.ReservaService;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private HabitacionService habitacionService;

    @Autowired
    private ReservaService reservaService;

    private Boolean checkFecha(Date inicio, Date termino) {
        return !inicio.after(termino);
    }

    private String checkReserva(String habitacionNumero, Date inicio, Date termino) {
        String habitacionId = "";
        List<ReservaModel> reservas = reservaService.getAllReservas();
        for(ReservaModel reserva:  reservas) {
            System.out.println("Habi "+reserva.getHabitacionId());
            if (reserva.getHabitacionId().equals(habitacionNumero))  {
                if (
                    ( (inicio.before(reserva.getInicio()) || inicio.equals(reserva.getInicio()) ) && (termino.equals(reserva.getInicio()) || termino.after(reserva.getInicio())) ) ||
                    ( (termino.after(reserva.getTermino()) || termino.equals(reserva.getTermino()) ) && (inicio.before(reserva.getTermino()) || inicio.equals(reserva.getTermino())) ) ||
                    ( inicio.after(reserva.getInicio()) && termino.before(reserva.getTermino()) )
                ) {
                    habitacionId = reserva.getHabitacionId();
                    break;
                }
            }
        }
        System.out.println("Reserva coincidente "+habitacionId);
        return habitacionId;
    }

    public List<HabitacionModel> getHabitacionDisponible() {
        List<ReservaModel> reservas = reservaService.getAllReservas();
        List<HabitacionModel> habitaciones = habitacionService.getAllHabitacion();
        List<HabitacionModel> habitacionDisponible = new ArrayList<HabitacionModel>();

        Date ahora = new Date();
        
        for(HabitacionModel habitacion: habitaciones) {
            int busy = 0;
            for(ReservaModel reserva: reservas) {
                if (habitacion.getHabitacionNumero().equals(reserva.getHabitacionId())) {
                    if (!ahora.after(reserva.getInicio()) && !ahora.before(reserva.getTermino())) {
                        busy = 0;
                    }
                }
            }
            if (busy != 1) {
                habitacionDisponible.add(habitacion);
            }
        }

        return habitacionDisponible;
    }

    // Mappings

    @GetMapping
    public CollectionModel<EntityModel<ReservaModel>> getAllReservas() {
        List<ReservaModel> reservas = reservaService.getAllReservas();

        List<EntityModel<ReservaModel>> reservaResource = reservas.stream()
            .map(r -> EntityModel.of(r, 
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReservas()).withSelfRel()
            ))
        .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReservas());
        CollectionModel<EntityModel<ReservaModel>> resources = CollectionModel.of(reservaResource, linkTo.withRel("reservas"));

        return resources;
    }

    @GetMapping("/{id}")
    public EntityModel<ReservaModel> getReservaById(@PathVariable Long id) {
        Optional<ReservaModel> reserva = reservaService.getReservaById(id);

        if (reserva.isPresent()) {
            return EntityModel.of(reserva.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getReservaById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReservas()).withRel("all-reservas"));
        } else {
            throw new ReservaNotFoundExcepcion("La reserva consultada no existe");
        }
                
    }

    @PostMapping("/reservar")
    public EntityModel<ReservaModel> setReserva(@RequestBody ReservaModel reserva) {
        Boolean sw = true;
        if (!checkFecha(reserva.getInicio(), reserva.getTermino())) {
            System.out.print("VALIDACOÓN DE FECHAS REALIZADA");
            sw = false;
        } else {
            // validar disponibldad de reserva
            if (!checkReserva(reserva.getHabitacionId(), reserva.getInicio(), reserva.getTermino()).equals("")) {
                System.out.print("VALIDACOÓN DE FECHAS DISPONIBILDAD REALIZADA");
                sw = false;
            }
        }

        EntityModel<ReservaModel> reservaResult = null;

        System.out.println("SW "+sw);

        if (sw) {
            ReservaModel createdReserva = reservaService.createReserva(reserva);
            reservaResult = EntityModel.of(createdReserva,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReservas()).withRel("all-reservas"));
        }

        return reservaResult;
    }

    @DeleteMapping("/anular/{id}")
    public void anularReserva(@PathVariable Long id) {
        for(ReservaModel r: reservaService.getAllReservas()) {
            if (r.getReservaId().equals(id)) {
                reservaService.deleteReserva(id);
                break;
            }
        }
    }

}
