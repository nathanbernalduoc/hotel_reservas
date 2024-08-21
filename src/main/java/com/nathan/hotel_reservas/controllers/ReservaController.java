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

    @GetMapping({"/habitacion/add", "/habitacion/add/"})
    public ResultModel addHabitacion(@RequestParam("habitacionNumero") String habitacionNumero, @RequestParam("piso") int piso, @RequestParam("camas") int camas, @RequestParam("observacion") String observacion) {

        this.habitacion.add(new HabitacionModel(habitacion.size()+1, habitacionNumero, piso, camas, observacion));
        return (new ResultModel("success", "Nueva "+habitacion.size()+" habitación."));

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
        if (checkHabitacion(habitacionNumero).equals("")) {
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

    private Boolean checkFecha(Date inicio, Date termino) {
        return !inicio.after(termino);
    }

    private String checkReserva(String habitacionNumero, Date inicio, Date termino) {
        String habitacionId = "";
        for(ReservaModel reserva:  reservas) {
            if (reserva.getHabitacionId().equals(habitacionNumero))  {
                if ((inicio.equals(reserva.getInicio()) && termino.equals(reserva.getTermino())) || (inicio.after(reserva.getInicio()) && termino.before(reserva.getTermino())) || (inicio.before(reserva.getInicio()) && termino.after(reserva.getInicio())) || (termino.after(reserva.getTermino()) && inicio.before(reserva.getTermino()))) {
                    habitacionId = reserva.getHabitacionId();
                    break;
                }
            }
        }
        return habitacionId;
    }

    private String checkHabitacion(String habitacionNumero) {
        String existe = "";
        for(HabitacionModel hab: this.habitacion) {
            if (hab.getHabitacionNumero().equals(habitacionNumero)) {
                existe = hab.getHabitacionNumero();
                break;
            }
        }
        return existe;
    }

    @GetMapping("/reserva/anular/{id}")
    public ResultModel anularReserva(@PathVariable int id) {
        ResultModel result = new ResultModel("success",  "Reserva anulada correctamente");
        int index = getReservaIndex(id);
        if (!checkReservasLen()) {
            result = new ResultModel("error", "No hay reservas vigentes.");
        } else if (index > -1) {
            ReservaModel reserva = reservas.get(id);
            if (reserva.equals(null)) {
                result = new ResultModel("error", "No se encuentra ninguna reserva con el identificador especificado.");
            } else {
                reservas.remove(reserva);
            }
        } else {
            ReservaModel reserva = reservas.get(index);
            reservas.remove(reserva);
        }
        return result;
    }

    private Boolean checkReservasLen() {
        return (reservas.size()>0);
    }

    private int getReservaIndex(int reservaId) {
        int index = -1;
        for(ReservaModel reserva: reservas) {
            System.out.println(reserva.getReservaId()+" / "+reservaId);
            if (reserva.getReservaId() == reservaId) {
                index++;
                break;
            }
        }
        return index;
    }

    @GetMapping("/reserva/list")
    public List<ReservaModel> anularReserva() {
        return reservas;
    }

    public ReservaController() {
        habitacion.add(new HabitacionModel(1, "A01", 1, 3, "Test #1"));
        habitacion.add(new HabitacionModel(2, "A02", 1, 1, "Test #2"));
        habitacion.add(new HabitacionModel(3, "B01", 2, 2, "Test #3"));
        habitacion.add(new HabitacionModel(4, "B02", 2, 3, "Test #4"));
        habitacion.add(new HabitacionModel(5, "C01", 3, 4, "Full"));
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
