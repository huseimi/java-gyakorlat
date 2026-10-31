package org.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "kapcsolat_uzenet")
public class KapcsolatUzenet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A név megadása kötelező.")
    @Size(max = 100, message = "A név legfeljebb 100 karakter lehet.")
    @Column(nullable = false, length = 100)
    private String nev;

    @NotBlank(message = "Az e-mail-cím megadása kötelező.")
    @Email(message = "Adj meg érvényes e-mail-címet.")
    @Size(max = 150, message = "Az e-mail-cím legfeljebb 150 karakter lehet.")
    @Column(nullable = false, length = 150)
    private String email;

    @NotBlank(message = "A tárgy megadása kötelező.")
    @Size(max = 150, message = "A tárgy legfeljebb 150 karakter lehet.")
    @Column(nullable = false, length = 150)
    private String targy;

    @NotBlank(message = "Az üzenet megadása kötelező.")
    @Size(max = 2000, message = "Az üzenet legfeljebb 2000 karakter lehet.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String uzenet;

    @Column(nullable = false)
    private LocalDateTime kuldesIdeje;

    public KapcsolatUzenet() {
    }

    public Long getId() {
        return id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTargy() {
        return targy;
    }

    public void setTargy(String targy) {
        this.targy = targy;
    }

    public String getUzenet() {
        return uzenet;
    }

    public void setUzenet(String uzenet) {
        this.uzenet = uzenet;
    }

    public LocalDateTime getKuldesIdeje() {
        return kuldesIdeje;
    }

    public void setKuldesIdeje(LocalDateTime kuldesIdeje) {
        this.kuldesIdeje = kuldesIdeje;
    }
}