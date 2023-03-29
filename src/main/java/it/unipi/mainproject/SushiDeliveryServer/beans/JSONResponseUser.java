/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.SushiDeliveryServer.beans;

/**
 *
 * @author Lorenzo
 */
public class JSONResponseUser extends JSONResponse{
    public Utente user;

    public JSONResponseUser(Utente user, boolean ok, String msg, int err) {
        super(ok, msg, err);
        this.user = user;
    }

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }
}
