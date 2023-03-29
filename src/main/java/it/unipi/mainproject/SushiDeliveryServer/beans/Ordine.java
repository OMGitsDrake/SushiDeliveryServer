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
@Table(name = "ordine", schema = ParametriRicorrenti.schema)
public class Ordine {
    @Id
    @Column(name = "id_ordine")
    private int id_ordine;
    @Column(name = "utente")
    private String utente;
    @Column(name = "fascia_oraria")
    private String fascia_oraria;
    @Column(name = "indirizzo")
    private String indirizzo;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "totale")
    private float totale;

    public Ordine() {
    }

    public Ordine(int id_ordine, String utente, String fascia_oraria, String indirizzo, String telefono, float totale) {
        this.id_ordine = id_ordine;
        this.utente = utente;
        this.fascia_oraria = fascia_oraria;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.totale = totale;
    }

    public int getId_ordine() {
        return id_ordine;
    }

    public void setId_ordine(int id_ordine) {
        this.id_ordine = id_ordine;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public String getFascia_oraria() {
        return fascia_oraria;
    }

    public void setFascia_oraria(String fascia_oraria) {
        this.fascia_oraria = fascia_oraria;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }
}
