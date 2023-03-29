/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.SushiDeliveryServer.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unipi.mainproject.SushiDeliveryServer.beans.JSONResponse;
import it.unipi.mainproject.SushiDeliveryServer.beans.JSONResponseMenu;
import it.unipi.mainproject.SushiDeliveryServer.beans.ParametriRicorrenti;
import it.unipi.mainproject.SushiDeliveryServer.beans.Piatto;
import it.unipi.mainproject.SushiDeliveryServer.repositories.DishRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Lorenzo
 */
@Controller
@RequestMapping(path="/dish")
public class DishController {
    @Autowired
    private DishRepository dr;
    
    @PostMapping(path="/list")
    public @ResponseBody String menuList(@RequestBody String body){
        String response;
        Gson gson = new Gson();
        try{
            JsonElement json = gson.fromJson(body, JsonElement.class);
            JsonObject req = json.getAsJsonObject();
            
            Connection c = DriverManager.getConnection(ParametriRicorrenti.connectionURL, ParametriRicorrenti.user, ParametriRicorrenti.pass);
            Statement sql = c.createStatement();
            
            List<Piatto> list = new ArrayList<>();
            if(req.get("tipo").getAsString().equals("pranzo")){
                ResultSet rs = sql.executeQuery("SELECT * FROM piatto_pranzo;");

                while(rs.next()){
                    list.add(new Piatto(rs.getInt("id_piatto"), rs.getString("nome"), rs.getString("ingredienti"), rs.getFloat("costo")));
                }
            }
            else
                list.addAll(dr.findAll());
            
            response = gson.toJson(new JSONResponseMenu(list, true, "(Menu) Dati recuperati con successo!", 0));
        } catch(SQLException sqle){
            response = gson.toJson(new JSONResponseMenu(null, false, sqle.getMessage() + "\n" + sqle.getSQLState(), 1));
        }
        return response;
    }
    
    @PostMapping(path="/starred")
    public @ResponseBody String starredList(@RequestBody String body){
        String response;
        Gson gson = new Gson();
        try{
            JsonElement json = gson.fromJson(body, JsonElement.class);
            JsonObject req = json.getAsJsonObject();
            
            Connection c = DriverManager.getConnection(ParametriRicorrenti.connectionURL, ParametriRicorrenti.user, ParametriRicorrenti.pass);
            Statement sql = c.createStatement();
            JsonObject u = req.get("u").getAsJsonObject();
            String queryString;
            if(req.get("tipo").getAsString().equals("pranzo"))
                queryString = "SELECT id_piatto, nome, ingredienti, costo "
                        + "FROM piatto_cena INNER JOIN preferiti ON id_piatto = piatto "
                        + "WHERE utente = '"+u.get("username").getAsString()+"' AND id_piatto <= 50;";
            else
                queryString = "SELECT id_piatto, nome, ingredienti, costo "
                        + "FROM piatto_cena INNER JOIN preferiti ON id_piatto = piatto "
                        + "WHERE utente = '"+u.get("username").getAsString()+"';";
            
            ResultSet rs = sql.executeQuery(queryString);
            
            List<Piatto> list = new ArrayList<>();
            while(rs.next()){
                list.add(new Piatto(rs.getInt("id_piatto"), rs.getString("nome"), rs.getString("ingredienti"), rs.getFloat("costo")));
            }
            
            response = gson.toJson(new JSONResponseMenu(list, true, "(Preferiti) Dati recuperati con successo!", 0));
        } catch(SQLException sqle){
            response = gson.toJson(new JSONResponseMenu(null, false, sqle.getMessage() + "\n" + sqle.getSQLState(), 1));
        }
        return response;
    }
    
    @PostMapping(path="/makestarred")
    public @ResponseBody String makeStarred(@RequestBody String body){
        String response;
        Gson gson = new Gson();
        try{
            JsonElement json = gson.fromJson(body, JsonElement.class);
            JsonObject req = json.getAsJsonObject();
            
            Connection c = DriverManager.getConnection(ParametriRicorrenti.connectionURL, ParametriRicorrenti.user, ParametriRicorrenti.pass);
            
            Statement sql = c.createStatement();
            String queryString = "SELECT count(*) AS conteggio FROM preferiti WHERE utente = '"+req.get("username").getAsString()+"';";
            ResultSet rs = sql.executeQuery(queryString);
            rs.next();
            
            if(rs.getInt("conteggio") >= 10){
                response = gson.toJson(new JSONResponse(false, "Massimo numero di preferiti raggiunto!", 2));
                return response;
            }
            
            PreparedStatement stmt;
            stmt = c.prepareStatement("INSERT INTO preferiti VALUES (?, ?);");
            stmt.setString(1, req.get("username").getAsString());
            stmt.setInt(2, req.get("id_piatto").getAsInt());
            stmt.execute();
            
            response = gson.toJson(new JSONResponse(true, "Dati inseriti correttamente!", 0));
        } catch(SQLException sqle){
            response = gson.toJson(new JSONResponse(false, sqle.getMessage() + "\n" + sqle.getSQLState(), 1));
        }
        return response;
    }
    
    @DeleteMapping(path="/removestarred")
    public @ResponseBody String removeStarred(@RequestBody String body){
        String response;
        Gson gson = new Gson();
        try{
            JsonElement json = gson.fromJson(body, JsonElement.class);
            JsonObject req = json.getAsJsonObject();
            
            Connection c = DriverManager.getConnection(ParametriRicorrenti.connectionURL, ParametriRicorrenti.user, ParametriRicorrenti.pass);
            PreparedStatement stmt;
            stmt = c.prepareStatement("DELETE FROM preferiti WHERE utente = ? AND piatto = ?;");
            stmt.setString(1, req.get("username").getAsString());
            stmt.setInt(2, req.get("id_piatto").getAsInt());
            stmt.execute();
            
            response = gson.toJson(new JSONResponse(true, "Dati rimossi correttamente!", 0));
        } catch(SQLException sqle){
            response = gson.toJson(new JSONResponse(false, sqle.getMessage() + "\n" + sqle.getSQLState(), 1));
        }
        return response;
    }
}