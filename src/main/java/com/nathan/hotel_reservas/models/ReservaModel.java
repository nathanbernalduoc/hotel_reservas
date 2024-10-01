package com.nathan.hotel_reservas.models;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "HO_RESERVA")

public class ReservaModel extends RepresentationModel<ReservaModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserva_sequence")
    @SequenceGenerator(name = "reserva_sequence", sequenceName = "seq_reserva_id", allocationSize = 1)
    @Column(name = "reserva_id")
    private Long reservaId; 
    @Column(name = "numero")
    private String habitacionId;
    @Column(name = "inicio")
    private Date inicio;
    @Column(name = "termino")
    private Date termino;
    @Column(name = "cliente")
    private String cliente;
    @Column(name = "reserva_flag")
    private int reservaFlag;

    public ReservaModel() {

    }

    public ReservaModel(Long reservaId, String habitacionId, Date inicio, Date termino, String cliente, int reservaFlag) {
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

    public void setTermino(Date fin) {
        this.termino = fin;
    }


    public Long getReservaId() {
        return reservaId;
    }


    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }
    
}
