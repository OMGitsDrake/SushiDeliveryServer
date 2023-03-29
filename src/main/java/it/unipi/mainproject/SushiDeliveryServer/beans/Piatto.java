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
@Table(name = "piatto_cena", schema = ParametriRicorrenti.schema)
public class Piatto {
    @Id
    @Column(name = "id_piatto")
    private int id_piatto;
    @Column(name = "nome")
    private String nome;
    @Column(name = "ingredienti")
    private String ingredienti;
    @Column(name = "costo")
    private float costo;

    public Piatto() {
    }

    public Piatto(int id_piatto, String nome, String ingredienti, float costo) {
        this.id_piatto = id_piatto;
        this.nome = nome;
        this.ingredienti = ingredienti;
        this.costo = costo;
    }

    public int getId_piatto() {
        return id_piatto;
    }

    public void setId_piatto(int id_piatto) {
        this.id_piatto = id_piatto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(String ingredienti) {
        this.ingredienti = ingredienti;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }
}
