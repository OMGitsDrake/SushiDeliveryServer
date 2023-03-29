/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.SushiDeliveryServer.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unipi.mainproject.SushiDeliveryServer.beans.JSONResponse;
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
@RequestMapping(path="/test")
public class TestController {
    @PostMapping(path="/server_con_post")
    public @ResponseBody String testPostCon(@RequestBody String body){
        String response;
        Gson gson = new Gson();
        JsonElement json = gson.fromJson(body, JsonElement.class);
        JsonObject req = json.getAsJsonObject();
        
        if(req.get("test").getAsString().equals("connessione"))
            response = gson.toJson(new JSONResponse(true, "Connessione stabilita!", 0));
        else
            response = gson.toJson(new JSONResponse(false, "Errore nella connessione", 1));
        return response;
    }
    
    @DeleteMapping(path="/server_con_delete")
    public @ResponseBody String testDeleteCon(@RequestBody String body){
        String response;
        Gson gson = new Gson();
        JsonElement json = gson.fromJson(body, JsonElement.class);
        JsonObject req = json.getAsJsonObject();
        
        if(req.get("test").getAsString().equals("connessione"))
            response = gson.toJson(new JSONResponse(true, "Connessione stabilita!", 0));
        else
            response = gson.toJson(new JSONResponse(false, "Errore nella connessione", 1));
        return response;
    }
}
