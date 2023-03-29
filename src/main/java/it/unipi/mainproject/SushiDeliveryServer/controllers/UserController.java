/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.SushiDeliveryServer.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unipi.mainproject.SushiDeliveryServer.beans.JSONResponse;
import it.unipi.mainproject.SushiDeliveryServer.beans.JSONResponseUser;
import it.unipi.mainproject.SushiDeliveryServer.beans.ParametriRicorrenti;
import it.unipi.mainproject.SushiDeliveryServer.beans.Utente;
import it.unipi.mainproject.SushiDeliveryServer.exceptions.ParameterException;
import it.unipi.mainproject.SushiDeliveryServer.repositories.UserRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Lorenzo
 */
@Controller
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    private UserRepository ur;
    
    @PostMapping(path="/sign")
    public @ResponseBody String signIn(@RequestBody String body){
        String response;
        Gson gson = new Gson();
        try {
            JsonElement json = gson.fromJson(body, JsonElement.class);
            JsonObject req = json.getAsJsonObject();
            
            Connection c = DriverManager.getConnection(ParametriRicorrenti.connectionURL, ParametriRicorrenti.user, ParametriRicorrenti.pass);
            Statement sql = c.createStatement();
            ResultSet rs = sql.executeQuery("SELECT username FROM utente WHERE username = "+ req.get("user"));
            
            if(rs.next())
                throw new ParameterException(1, "Nome utente non disponibile!");
            
            PreparedStatement stmt;
            stmt = c.prepareStatement("INSERT INTO utente (username, `password`, email) VALUES (?, ?, ?, ?)");
            stmt.setString(1, req.get("user").getAsString());
            stmt.setString(2, req.get("psw").getAsString());
            stmt.setString(3, req.get("mail").getAsString());
            stmt.setString(4, req.get("bdate").getAsString());
            stmt.execute();
            
            response = gson.toJson(new JSONResponse(true, "Utente registrato con successo!", 0));
        } catch (ParameterException pe) {
            response = gson.toJson(new JSONResponse(false, pe.getMessage(), pe.getCode()));
        } catch(SQLException sqle){
            response = gson.toJson(new JSONResponse(false, sqle.getMessage(), 3));
        }
        return response;
    }
    
    @PostMapping(path="/login")
    public @ResponseBody String logIn(@RequestBody String body){
        String response;
        Gson gson = new Gson();
        try{
            JsonElement json = gson.fromJson(body, JsonElement.class);
            JsonObject req = json.getAsJsonObject();
            
            String psw_hash = req.get("pass").getAsString();
            
            Connection c = DriverManager.getConnection(ParametriRicorrenti.connectionURL, ParametriRicorrenti.user, ParametriRicorrenti.pass);
            Statement sql = c.createStatement();
            ResultSet rs = sql.executeQuery("SELECT `password` FROM utente WHERE username = "+ req.get("user"));
            
            if(!rs.next())
                throw new SQLException("Username non trovato");
            
            if(!BCrypt.checkpw(psw_hash, rs.getString("password")))
                throw new ParameterException(2, "Credenziali errate!");
            
            Utente u = ur.findByUsername(req.get("user").getAsString());
            
            response = gson.toJson(new JSONResponseUser(u, true, "Login avvenuto con successo!", 0));
        } catch (SQLException e) {
            response = gson.toJson(new JSONResponseUser(null, false, e.getMessage(), 3));
        } catch (ParameterException pe){
            response = gson.toJson(new JSONResponseUser(null, false, pe.getMessage(), pe.getCode()));
        }
        return response;
    }
}