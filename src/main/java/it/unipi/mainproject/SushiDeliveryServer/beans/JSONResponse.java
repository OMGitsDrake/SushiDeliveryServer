/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.SushiDeliveryServer.beans;

import java.io.Serializable;

/**
 *
 * @author Lorenzo
 */
public class JSONResponse implements Serializable{
    public boolean ok;
    public String msg;
    public int err;

    public JSONResponse(boolean ok, String msg, int err) {
        this.ok = ok;
        this.msg = msg;
        this.err = err;
    }
    
    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public boolean isOk() {
        return ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
