package com.nathan.hotel_reservas.controllers;

import java.sql.Date;
import java.util.List;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nathan.hotel_reservas.models.HabitacionModel;
import com.nathan.hotel_reservas.models.ReservaModel;
import com.nathan.hotel_reservas.models.ResultModel;

@RestController
public class ReservaController {

    private List<HabitacionModel> habitacion = new ArrayList<>();
    private List<ReservaModel> reservas = new ArrayList<>();


    public ReservaController() {
        habitacion.add(new HabitacionModel(1, "A01", 1, 3, "Test #1"));
        habitacion.add(new HabitacionModel(2, "A02", 1, 1, "Test #2"));
        habitacion.add(new HabitacionModel(3, "B01", 2, 2, "Test #3"));
        habitacion.add(new HabitacionModel(4, "B02", 2, 3, "Test #4"));
        habitacion.add(new HabitacionModel(5, "C01", 3, 4, "Full"));
    }

    /*
     * Functions
     */

    private Boolean checkFecha(Date inicio, Date termino) {
        return !inicio.after(termino);
    }

    private String checkReserva(String habitacionNumero, Date inicio, Date termino) {
        String habitacionId = "";
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
        HabitacionModel habitacion = new HabitacionModel();
        for(HabitacionModel hab: this.habitacion) {
            if (hab.getHabitacionNumero().equals(habitacionNumero)) {
                habitacion = hab;
            }
        }
        return habitacion;
    }

private Boolean checkReservasLen() {
        return (reservas.size()>0);
    }

    private ReservaModel getReservaById(int reservaId) {
        ReservaModel reserva = new ReservaModel();
        for(ReservaModel r: reservas) {
            if (r.getReservaId() == reservaId) {
                reserva = r;
            }
        }
        return reserva;
    }

    /*
     * APIs
     */

    @GetMapping({"/habitacion/add", "/habitacion/add/"})
    public ResultModel addHabitacion(@RequestParam("habitacionNumero") String habitacionNumero, @RequestParam("piso") int piso, @RequestParam("camas") int camas, @RequestParam("observacion") String observacion) {
        ResultModel result = new ResultModel("error", "La habitación "+habitacionNumero+" referenciada ya existe.");
        if (checkHabitacion(habitacionNumero).getId() == 0) {
            this.habitacion.add(new HabitacionModel(habitacion.size()+1, habitacionNumero, piso, camas, observacion));
            result = new ResultModel("success", "Nueva "+habitacion.size()+" habitación.");
        }
        return result;
        
    }

    @GetMapping("/habitacion/unset/{id}")
    public ResultModel addHabitacion(@PathVariable int id) {

        Boolean sw = false;

        for (HabitacionModel h: habitacion) {
            if (h.getId() == id) {
                habitacion.remove(h);
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
        return habitacion;
    }

    @GetMapping({"/reserva/reservar/", "/reserva/reservar"})
    public ResultModel setReserva(@RequestParam("habitacionNumero") String habitacionNumero, @RequestParam("inicio") Date inicio, @RequestParam("termino") Date termino, @RequestParam("cliente") String cliente) {
        ResultModel result = new ResultModel("success", "Reserva registrada con éxito.");
        Boolean sw = true;
        // validar habitación referenciada
        if (checkHabitacion(habitacionNumero).getId() == 0) {
            result = new ResultModel("error", "La habitación referenciada no existe.");
            sw = false;
        } else if (!checkFecha(inicio, termino)) {
            result = new ResultModel("error", "La fecha de inicio no puede ser posterior a la fecha de término.");
            sw = false;
        } else {
            // validar disponibldad de reserva
            if (!checkReserva(habitacionNumero, inicio, termino).equals("")) {
                result = new ResultModel("error", "Fecha referenciada no disponible.");
                sw = false;
            }
        }

        if (sw) {
            reservas.add(new ReservaModel(reservas.size()+1, habitacionNumero, inicio, termino, cliente, 1));
        }
        return result;
    }

    @GetMapping("/reserva/list")
    public List<ReservaModel> anularReserva() {
        return reservas;
    }

    @GetMapping("/reserva/anular/{id}")
    public ResultModel anularReserva(@PathVariable int id) {
        ResultModel result = new ResultModel("success",  "Reserva "+id+" anulada correctamente");
        if (!checkReservasLen()) {
            result = new ResultModel("error", "No hay reservas vigentes.");
        } else {
            ReservaModel reserva = getReservaById(id);
            if (reserva.getReservaId() == 0) {
                result = new ResultModel("error", "No se encuentra ninguna reserva con el identificador especificado.");
            } else {
                reservas.remove(reserva);
            }
        }
        return result;
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
