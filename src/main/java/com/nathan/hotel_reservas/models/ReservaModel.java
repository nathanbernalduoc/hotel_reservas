package com.nathan.hotel_reservas.models;

import java.sql.Date;

public class ReservaModel {

    private int reservaId;
    private String habitacionId;
    private Date inicio;
    private Date termino;
    private String cliente;
    private int reservaFlag;

    public ReservaModel() {
    }

    public ReservaModel(int reservaId, String habitacionId, Date inicio, Date termino, String cliente, int reservaFlag) {
        this.reservaId = reservaId;
        this.habitacionId = habitacionId;
        this.inicio = inicio;
        this.termino = termino;
        this.cliente = cliente;
        this.reservaFlag = reservaFlag;
    }


    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getReservaFlag() {
        return reservaFlag;
    }

    public void setReservaFlag(int reservaFlag) {
        this.reservaFlag = reservaFlag;
    }

    public String getHabitacionId() {
        return habitacionId;
    }

    public void setHabitacionId(String habitacionId) {
        this.habitacionId = habitacionId;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getTermino() {
        return termino;
    }

    public void setTermino(Date termino) {
        this.termino = termino;
    }


    public int getReservaId() {
        return reservaId;
    }


    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }
    
}
