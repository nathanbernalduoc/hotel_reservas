package com.nathan.hotel_reservas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "HO_HABITACION")

public class HabitacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "habitacion_sequence")
    @SequenceGenerator(name = "habitacion_sequence", sequenceName = "seq_habitacion_id", allocationSize = 1)
    @Column(name = "habitacion_id")
    private Long id;
    @Column(name = "numero")
    private String habitacionNumero;
    @Column(name = "piso")
    private int piso;
    @Column(name = "camas")
    private int camas;
    @Column(name = "observacion")
    private String observacion;

    public HabitacionModel() {

    }

    public HabitacionModel(Long id, String habitacionNumero, int piso, int camas, String observacion) {
        this.id = id;
        this.habitacionNumero = habitacionNumero;
        this.piso = piso;
        this.camas = camas;
        this.observacion = observacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

