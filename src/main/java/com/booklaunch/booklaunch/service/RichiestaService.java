package com.booklaunch.booklaunch.service;

import com.booklaunch.booklaunch.dto.RichiestaDTO;

import java.util.List;

/**
 * Il livello Service contiene la business logic.
 * Definisce le funzionalità fornite, come sono vi si accede e quali parametri sono passati e ritornati
 */
public interface RichiestaService {

    RichiestaDTO create_richiesta(RichiestaDTO richiestaDTO);

    Boolean delete_richiesta(Long id_richiesta);

    List<RichiestaDTO> findAll();

    List<RichiestaDTO> getRichiesteUtente(Long id_utente);

    List<RichiestaDTO> getRichiesteAdmin(Long id_admin);

    List<RichiestaDTO> getRichiesteByUtente(Long id_utente);

    Boolean accetta_richiesta(Long id_richiesta);

    Boolean rifiuta_richiesta(Long id_richiesta);
}
