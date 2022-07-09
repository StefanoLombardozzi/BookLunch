package com.booklaunch.booklaunch.service;

import com.booklaunch.booklaunch.dto.UtenteDTO;
import com.booklaunch.booklaunch.model.Utente;

import java.util.List;

public interface UtenteService {

    UtenteDTO create_utente(UtenteDTO utenteDTO);  //registrazione
    UtenteDTO create_admin(Long id_utente);  //registrazione
    UtenteDTO update_utente(UtenteDTO utenteDTO);
    Boolean elimina_utente(Long id_utente);
    List<UtenteDTO> findAll();
    UtenteDTO findByEmail(String email);
    List<UtenteDTO> findByCognome(String cognome);
}
