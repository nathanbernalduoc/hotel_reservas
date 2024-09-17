package com.nathan.hotel_reservas.controllers;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nathan.hotel_reservas.models.HabitacionModel;
import com.nathan.hotel_reservas.models.ReservaModel;
import com.nathan.hotel_reservas.models.ResultModel;
import com.nathan.hotel_reservas.service.HabitacionService;
import com.nathan.hotel_reservas.service.ReservaService;

@RestController
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
        List<ReservaModel> reservas = reservaService.getAllReserva();
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

    private HabitacionModel checkHabitacion(String habitacionNumero) {
        HabitacionModel habitacion = null;
        List<HabitacionModel> habitaciones = habitacionService.getAllHabitacion();
        for(HabitacionModel hab: habitaciones) {
            System.out.println("COMPARANDO "+hab.getHabitacionNumero()+" "+habitacionNumero);
            if (hab.getHabitacionNumero().equals(habitacionNumero)) {
                habitacion = hab;
            }
        }
        System.out.println(habitacion);
        return habitacion;
    }

    @PostMapping({"/habitacion/add", "/habitacion/add/"})
    public ResultModel addHabitacion(@RequestBody HabitacionModel habitacion) {
        ResultModel result = new ResultModel("error", "La habitación referenciada ya existe.");
        System.out.println("Habitacion recibida "+habitacion.getHabitacionNumero());
        if (checkHabitacion(habitacion.getHabitacionNumero()) == null) {
            habitacionService.createHabitacion(habitacion);
            result = new ResultModel("success", "Nueva habitación "+habitacion.getHabitacionNumero()+" creada con éxito.");
        }
        return result;
        
    }

    @GetMapping("/habitacion/unset/{id}")
    public ResultModel addHabitacion(@PathVariable Long id) {

        Boolean sw = false;
        List<HabitacionModel> habitacion = habitacionService.getAllHabitacion();
        for (HabitacionModel h: habitacion) {
            if (h.getId() == id) {
                habitacionService.deleteHabitacion(id);
                sw = true;
                break;
            }
        }

        ResultModel result = new ResultModel("success", "Habitación eliminada.");
        if (!sw) {
            result =  new ResultModel("error", "La habitación "+id+" no existe");
        }
        return result;

    }

    @GetMapping("/habitacion/list")
    public List<HabitacionModel> listHabitacion() {
        return habitacionService.getAllHabitacion();
    }

    @PostMapping("/reserva/reservar")
    public ResultModel setReserva(@RequestBody ReservaModel reserva) {
        ResultModel result = new ResultModel("success", "Reserva registrada con éxito.");
        Boolean sw = true;
        System.out.println("Habitacion recibida "+reserva.getHabitacionId());
        // validar habitación referenciada
        if (checkHabitacion(reserva.getHabitacionId()) == null) {
            result = new ResultModel("error", "La habitación referenciada no existe.");
            System.out.print("VALIDACOÓN DE HABITACION REALIZADA");
            sw = false;
        } else if (!checkFecha(reserva.getInicio(), reserva.getTermino())) {
            result = new ResultModel("error", "La fecha de inicio no puede ser posterior a la fecha de término.");
            System.out.print("VALIDACOÓN DE FECHAS REALIZADA");
            sw = false;
        } else {
            // validar disponibldad de reserva
            if (!checkReserva(reserva.getHabitacionId(), reserva.getInicio(), reserva.getTermino()).equals("")) {
                result = new ResultModel("error", "Fecha referenciada no disponible.");
                System.out.print("VALIDACOÓN DE FECHAS DISPONIBILDAD REALIZADA");
                sw = false;
            }
        }

        if (sw) {
            reservaService.createReserva(reserva);
        }
        return result;
    }

    @GetMapping("/reserva/list")
    public List<ReservaModel> getReservaList() {
        return reservaService.getAllReserva();
    }

    @DeleteMapping("/reserva/anular/{id}")
    public ResultModel anularReserva(@PathVariable Long id) {
        ResultModel result = new ResultModel("success", "Reserva anulada correctamente.");
        int sw = 0;
        for(ReservaModel r: reservaService.getAllReserva()) {
            if (r.getReservaId().equals(id)) {
                sw = 1;
            }
        }
        if (sw == 1) {
            reservaService.deleteReserva(id);
        } else {
            result = new ResultModel("error", "La reserva especificada no existe");
        }
        return result;
    }

    @GetMapping({"/reserva/disponibilidad", "/reserva/disponibilidad/"})
    public List<HabitacionModel> getDisponibilidad() {
        List<HabitacionModel> habitaciones = habitacionService.getAllHabitacion();
        List<HabitacionModel> disponible = new ArrayList<HabitacionModel>();
        List<ReservaModel> reservas = reservaService.getAllReserva();
        for(HabitacionModel h: habitaciones) {
            int sw = 0;
            for(ReservaModel r: reservas) {
                System.out.println("Comparando "+h.getHabitacionNumero()+" "+r.getHabitacionId());
                if (h.getHabitacionNumero().equals(r.getHabitacionId())) {
                    sw = 1;
                } 
            }
            if (sw != 1) {
                disponible.add(h);
            }
        }
        return disponible;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultModel IdNotFoundResponse(Exception e){
        String errorMsg = e.getMessage();
        String msg = "";
        if (errorMsg.indexOf("Date") > 0) {
            msg = "(Posible fecha errónea, formato debe ser YYYY-MM-DD)";
        }
        return (new ResultModel("error", "Se ha detectado una excepción "+msg+": "+e.getMessage()));
    }

}
