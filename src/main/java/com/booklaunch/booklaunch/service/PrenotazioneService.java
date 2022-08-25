package com.booklaunch.booklaunch.service;

import com.booklaunch.booklaunch.dto.PrenotazioneDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Il livello Service contiene la business logic.
 * Definisce le funzionalità fornite, come vi si accede e quali parametri sono passati e ritornati
 */
public interface PrenotazioneService {

    List<PrenotazioneDTO> findAll();

    PrenotazioneDTO insertPrenotazione(PrenotazioneDTO prenotazioneDTO);

    Boolean deletePrenotazione(Long id_prenotazione);

    List<PrenotazioneDTO> findByData(LocalDate data_prenotazione);

    int countByPranzoAndData(LocalDate data_prenotazione);

    int countByCenaAndData(LocalDate data_prenotazione);

    int countByColazioneAndData(LocalDate data_prenotazione);

    int countBySacchettoPranzoAndData(LocalDate data_prenotazione);

    int countBySacchettoCenaAndData(LocalDate data_prenotazione);

    PrenotazioneDTO updatePrenotazione(PrenotazioneDTO prenotazioneDTO);

    List<PrenotazioneDTO> findByUtente(Long id_utente);
}
