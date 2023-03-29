/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.SushiDeliveryServer.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Lorenzo
 */
@Entity
@Table(name = "utente", schema = ParametriRicorrenti.schema)
public class Utente {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "punti")
    private int punti;
    @Column(name = "bdate")
    private String bdate;

    public Utente() {
    }
    
    public Utente(String username, String password, String email, int punti, String bdate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.punti = punti;
        this.bdate = bdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return email;
    }

    public void setMail(String email) {
        this.email = email;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    @Override
    public String toString() {
        return "Utente{" + "username=" + username + ", email=" + email + ", punti=" + punti + ", bdate=" + bdate + '}';
    }
    
    
}
