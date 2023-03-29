/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.SushiDeliveryServer.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unipi.mainproject.SushiDeliveryServer.beans.JSONResponse;
import it.unipi.mainproject.SushiDeliveryServer.beans.ParametriRicorrenti;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Lorenzo
 */
@Controller
@RequestMapping(path="order")
public class OrderController {
    
    @PostMapping(path="/confirm")
    public @ResponseBody String confermaOrdine(@RequestBody String body){
        String response;
        Gson gson = new Gson();
        
        try{
            JsonElement json = gson.fromJson(body, JsonElement.class);
            JsonObject req = json.getAsJsonObject();
            
            Connection c = DriverManager.getConnection(ParametriRicorrenti.connectionURL, ParametriRicorrenti.user, ParametriRicorrenti.pass);
            PreparedStatement stmt = c.prepareStatement("INSERT INTO ordine(utente, orario_ordine, fascia_oraria, indirizzo, telefono, totale) "
                    + "VALUES(?, current_timestamp(), ?,?,?,?);");
            stmt.setString(1, req.get("user").getAsString());
            stmt.setString(2, req.get("orario").getAsString());
            stmt.setString(3, req.get("indirizzo").getAsString());
            stmt.setString(4, req.get("telefono").getAsString());
            stmt.setFloat(5, req.get("totale").getAsFloat());
            stmt.execute();
            
            stmt = c.prepareStatement("UPDATE utente SET punti = punti + ? WHERE username = ?;");
            stmt.setInt(1, req.get("puntiAcquisiti").getAsInt());
            stmt.setString(2, req.get("user").getAsString());
            stmt.execute();
            
            response = gson.toJson(new JSONResponse(true, "Ordine registrato con successo!", 0));
        } catch(SQLException sqle){
            response = gson.toJson(new JSONResponse(false, sqle.getMessage() + "\n" + sqle.getSQLState(), 1));
        }
        
        return response;
    }
    
    @PostMapping(path="/choose")
    public @ResponseBody String sceltaOrario(@RequestBody String body){
        String response;
        Gson gson = new Gson();
        
        try{
            JsonElement json = gson.fromJson(body, JsonElement.class);
            JsonObject req = json.getAsJsonObject();
            
            Connection c = DriverManager.getConnection(ParametriRicorrenti.connectionURL, ParametriRicorrenti.user, ParametriRicorrenti.pass);
            Statement sql = c.createStatement();
            String queryString = "SELECT count(*) as conteggio FROM ordine WHERE fascia_oraria = " + req.get("fascia_oraria") + ";";
            
            ResultSet rs = sql.executeQuery(queryString);
            rs.next();
            
            boolean ok;
            String msg;
            if(rs.getInt("conteggio") >= 4){
                ok = false;
                msg = "Orari al completo per quella fascia oraria!";
            } else {
                ok = true;
                msg = "C'è disponibilità con gli orari!";
            }
            
            response = gson.toJson(new JSONResponse(ok, msg, 0));
        } catch(SQLException sqle){
            response = gson.toJson(new JSONResponse(false, sqle.getMessage() + "\n" + sqle.getSQLState(), 1));
        }
        
        return response;
    }
}
