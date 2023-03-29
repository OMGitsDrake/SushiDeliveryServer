/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.SushiDeliveryServer.beans;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lorenzo
 */
public class JSONResponseMenu extends JSONResponse{
    public List<Piatto> menu = new ArrayList<>();

    public JSONResponseMenu(List<Piatto> l, boolean ok, String msg, int err) {
        super(ok, msg, err);
        this.menu.addAll(l);
    }

    public List<Piatto> getL() {
        return menu;
    }

    public void setL(List<Piatto> l) {
        this.menu = l;
    }
}
