package org.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "rendeles")
public class Rendeles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "az")
    private Long az;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pizzanev", nullable = false)
    private Pizza pizza;

    @Column(name = "darab", nullable = false)
    private Integer darab;

    @Column(name = "felvetel", nullable = false)
    private LocalDateTime felvetel;

    @Column(name = "kiszallitas", nullable = false)
    private LocalDateTime kiszallitas;

    public Rendeles() {
    }

    public Long getAz() {
        return az;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Integer getDarab() {
        return darab;
    }

    public void setDarab(Integer darab) {
        this.darab = darab;
    }

    public LocalDateTime getFelvetel() {
        return felvetel;
    }

    public void setFelvetel(LocalDateTime felvetel) {
        this.felvetel = felvetel;
    }

    public LocalDateTime getKiszallitas() {
        return kiszallitas;
    }

    public void setKiszallitas(LocalDateTime kiszallitas) {
        this.kiszallitas = kiszallitas;
    }
}