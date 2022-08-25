package com.booklaunch.booklaunch.service;

import com.booklaunch.booklaunch.dto.UtenteDTO;

import java.util.List;

/**
 * Il livello Service contiene la business logic.
 * Definisce le funzionalità fornite, come sono vi si accede e quali parametri sono passati e ritornati
 */
public interface UtenteService {

    UtenteDTO create_utente(UtenteDTO utenteDTO);
    UtenteDTO create_admin(Long id_utente);
    UtenteDTO update_utente(UtenteDTO utenteDTO);
    Boolean elimina_utente(Long id_utente);
    List<UtenteDTO> findAll();
    UtenteDTO findByEmail(String email);
    List<UtenteDTO> findByCognome(String cognome);
}
