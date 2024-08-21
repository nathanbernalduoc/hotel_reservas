package com.nathan.hotel_reservas.models;

public class HabitacionModel {

    private int id;
    private String habitacionNumero;
    private int piso;
    private int camas;
    private String observacion;

    public HabitacionModel(int id, String habitacionNumero, int piso, int camas, String observacion) {
        this.id = id;
        this.habitacionNumero = habitacionNumero;
        this.piso = piso;
        this.camas = camas;
        this.observacion = observacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHabitacionNumero() {
        return habitacionNumero;
    }

    public void setHabitacionNumero(String habitacionNumero) {
        this.habitacionNumero = habitacionNumero;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public int getCamas() {
        return camas;
    }

    public void setCamas(int camas) {
        this.camas = camas;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
}

