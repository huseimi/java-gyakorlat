package org.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pizza")
public class Pizza {

    @Id
    @Column(name = "nev", length = 100, nullable = false)
    private String nev;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kategorianev", nullable = false)
    private Kategoria kategoria;

    @Column(name = "vegetarianus", nullable = false)
    private boolean vegetarianus;

    public Pizza() {
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public Kategoria getKategoria() {
        return kategoria;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }

    public boolean isVegetarianus() {
        return vegetarianus;
    }

    public void setVegetarianus(boolean vegetarianus) {
        this.vegetarianus = vegetarianus;
    }
}