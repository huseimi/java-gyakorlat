package org.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "kategoria")
public class Kategoria {

    @Id
    @Column(name = "nev", length = 50, nullable = false)
    private String nev;

    @Column(name = "ar", nullable = false)
    private Integer ar;

    public Kategoria() {
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public Integer getAr() {
        return ar;
    }

    public void setAr(Integer ar) {
        this.ar = ar;
    }
}