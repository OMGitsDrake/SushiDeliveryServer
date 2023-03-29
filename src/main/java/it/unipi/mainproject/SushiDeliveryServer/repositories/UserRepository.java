/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.unipi.mainproject.SushiDeliveryServer.repositories;

import it.unipi.mainproject.SushiDeliveryServer.beans.Utente;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Lorenzo
 */
public interface UserRepository extends CrudRepository<Utente, Integer>{
    Utente findByUsername(String n);
    Utente save(Utente u);
}
